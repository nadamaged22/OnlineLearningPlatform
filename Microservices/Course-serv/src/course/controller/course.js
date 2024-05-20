import axios from "axios";
import courseModel from "../../../DB/model/course.model.js"

import {
	ReasonPhrases,
	StatusCodes,
	getReasonPhrase,
	getStatusCode,
} from 'http-status-codes';
import mongoose from "mongoose";
const fetchUserData = async(token)=>
    {
        try{
        const response = await axios.get(`http://localhost:3001/user/`,{
            headers:{
                authorization:token,
            },
        } );
        const data = response.data;
    return data;
       }catch(error)
       {
        console.error('Error fetching user data',error);
        throw new Error('Failed to fetch user data');
       }
    };
    const fetchAdminData = async(token)=>
    {
        try{
        const response = await axios.get(`http://localhost:3001/user/Admin`,{
            headers:{
                authorization:token,
            },
        } );
        const data = response.data;
    return data;
       }catch(error)
       {
        console.error('Error fetching user data',error);
        throw new Error('Failed to fetch user data');
       }
    };
// ADD Course -> Instructor 
export const addCourse = async(req,res,next)=>
{
    const {authorization}=req.headers
    // const {name,category,capacity,duration,rating,enrolledStudents}=req.body

    const checkrole = await fetchUserData (authorization);
    
    if(checkrole.users.role =='Instructor')
    {
        const existingCourse = await courseModel.findOne({ name:req.body.name, 'addedBY.id': checkrole.users._id });

        if (existingCourse) {
            return res.status(StatusCodes.BAD_REQUEST).json({
                message: "A course with this name already exists. Please choose a different name."
            });
        }
        const courseData = {
            ...req.body,
            addedBY: {
              email: checkrole.users.email,
              id: checkrole.users._id
            }
        };
    
        const course = await courseModel.create(courseData)
       return res.status(StatusCodes.CREATED).json({message:"Done",course,status:getReasonPhrase(StatusCodes.CREATED)})
    }
    else 
    {
        throw new Error("Not Authorized");
    }
}
export const updateCourse=async(req,res,next)=>
{
    //check by id category already exist
    const {courseId}=req.params
    const {authorization}=req.headers
    const checkrole = await fetchAdminData (authorization);
    if(checkrole.users.role=='Admin'){
        const Course=await courseModel.findById(courseId)
        if(!Course){
            return next (new Error ("Invalid_CourseID",{cause:400}))
        }
        if(req.body.name)//if the req to update is name
        {
            if(Course.name==req.body.name){
                return next (new Error("PLEASE ENTER DIFF NAME FROM THE OLD NAME",{cause:409}))
            }else{
                Course.name=req.body.name
            }
        }
        if(req.body.duration){
            Course.duration=req.body.duration
        }if(req.body.category){
            Course.category=req.body.category
        }if(req.body.capacity){
            Course.capacity=req.body.capacity
        }
        await Course.save()
        res.status(200).json({message:"UPDATED SUCCESS!",Course})
    }else 
    {
        throw new Error("Not Authorized");
    }  
   
}

export const deleteCourse=async(req,res,next)=>{
    const{id}=req.params
    const {authorization}=req.headers
    const checkrole = await fetchAdminData (authorization);
    if(checkrole.users.role=='Admin'){
        const CourseExist= await courseModel.findByIdAndDelete(id)
        if(!CourseExist){
            return next(new Error("IN_VALID Course ID!",{cause:400}))
        }
        res.status(200).json({message:"DELETED SUCCESS!",DeletedCourse:CourseExist})

    }
    else 
    {
        throw new Error("Not Authorized");
    }  

}
export const updateCourseStatus=async(req,res,next)=>
{
    //check by id category already exist
    const {courseId}=req.params
    const {authorization}=req.headers
    const checkrole = await fetchAdminData (authorization);
    if(checkrole.users.role=='Admin'){
        const Course=await courseModel.findById(courseId)
        if(!Course){
            return next (new Error ("Invalid_CourseID",{cause:400}))
        }
        if(req.body.status){
            Course.status=req.body.status
        }
        await Course.save()
        res.status(200).json({message:"Course Status Update SUCCESS!",Course})
    }else 
    {
        throw new Error("Not Authorized");
    }  
   
}


// view Course Info by ID -> All
export const ViewCourseInfo= async (req, res, next) => {
    try {
        const { courseId } = req.params;
        const courseWithReviews = await courseModel.aggregate([
            {
                $match: { _id:new mongoose.Types.ObjectId(courseId) }
            },
            {
                $lookup: {
                    from: 'reviews',
                    localField: '_id',
                    foreignField: 'addedTo.id',
                    as: 'reviews'
                }
            },
            {
                $project: {
                    _id: 1,
                    name: 1, // Include other course fields as needed
                    category: 1,
                    capacity:1,
                    duration:1,
                    rating:1,
                    status:1,
                    addedBY:1,
                    averageratings: 1,
                    reviews: 1
                }
            }
        ]);

        if (!courseWithReviews.length) {
            return res.status(404).json({ error: 'Course not found' });
        }

        res.json(courseWithReviews[0]);
    } catch (error) {
        next(error);
    }
};
// Search course by name
export const SearchByName = async(req,res,next)=>
    {
      const {searchkey}=req.query
      const course=await courseModel.find({
        name:{
            $regex: new RegExp(searchkey, 'i') // Perform case-insensitive search
        }
      })
      if(course.length == 0)
    {
        return next (new ErrorClass('This course is not found',StatusCodes.NOT_FOUND)) 
    }
      res.json({course})
    }
// Search course by category
export const SearchByCategory = async(req,res,next)=>
{
      const {searchkey}=req.query
      const course=await courseModel.find({
        category:{
            $regex: new RegExp(searchkey, 'i') // Perform case-insensitive search
        }
      })
      if(course.length == 0)
    {
        return next (new ErrorClass('This Category is not found',StatusCodes.NOT_FOUND)) 
    }
      res.json({course})
}
// Search course by sorting rating
export const SearchByRating= async(req,res,next)=>
{
    const courses= await courseModel.find().sort({rating:-1})
    res.json({courses})
}
export const getAllCourses = async(req,res)=>
{
    const courses= await courseModel.find({})
    return res.json({message : "Done",courses})
}
export const getPendingCourses = async(req,res)=>
{
    const {authorization}=req.headers
    const checkrole = await fetchAdminData (authorization);
    if(checkrole.users.role=='Admin'){
        const pendingCourses = await courseModel.find({ status: 'Pending' });
        return res.json({ message: "Done", courses: pendingCourses });

    }else{
        throw new Error("Not Authorized");
    }

    
     
    
}





