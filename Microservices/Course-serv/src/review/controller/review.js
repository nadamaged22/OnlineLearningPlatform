import axios from "axios";
import reviewModel from "../../../DB/model/review.model.js";
import courseModel from "../../../DB/model/course.model.js";
import {
	ReasonPhrases,
	StatusCodes,
	getReasonPhrase,
	getStatusCode,
} from 'http-status-codes';
// Fetch Data
const fetchUserData = async(token)=>
    {
        try{
        const response = await axios.get(`http://localhost:3001/user/`,{
            headers:{
                authorization:token,
            },
        } );
        const data = response.data;
        console.log(data);
    return data;
       }catch(error)
       {
        console.error('Error fetching user data',error);
        throw new Error('Failed to fetch user data');
       }
    };
// ADD Review
export const addReviewAndUpdateAvgRating = async(req, res, next) => {
    const { authorization } = req.headers;
    const { courseId } = req.params;

  
       const checkrole = await fetchUserData(authorization);

       if (checkrole.users.role !== 'Student') {
           throw new Error("Not Authorized");
       }

       const existingCourse = await courseModel.findById(courseId);
       if (!existingCourse) {
           throw new Error("Invalid Course ID");
       }

       const reviewData = {
           ...req.body,
           addedBY: {
               email: checkrole.users.email,
               id: checkrole.users._id
           },
           addedTo: {
               id: existingCourse._id
           }
       };

       const review = await reviewModel.create(reviewData);

       // Calculate average rating
       const reviews = await reviewModel.find({ "addedTo.id": courseId });
       const totalRatings = reviews.reduce((sum, review) => sum + review.rating, 0);
       const averageRating = totalRatings / reviews.length;

       // Update course with new average rating
       await courseModel.findByIdAndUpdate(courseId, { rating: averageRating });

       // Update all reviews for this course with the average rating
       await reviewModel.updateMany({ "addedTo.id": courseId }, { averageratings: averageRating });

       res.status(StatusCodes.CREATED).json({ message: "Review added and average rating updated", review, averageRating });
   
   };