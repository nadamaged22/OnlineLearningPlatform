import { Schema } from "mongoose";
import { model } from "mongoose";

const courseSchema = new Schema({
    name:{type : String , required : true},
    category:{type : String ,required : true},
    capacity :{type:String,required:true},
    duration: {type:Number,required:true},
    status:{type:String,default:'Pending',enum:['Pending','Accept','Reject']},
    addedBY: {
        id: {type: Schema.Types.ObjectId},
        email: String,
    },
    rating: { type: Number, default: 0 }, // Initialize rating to 0
    reviews: [{ type: Schema.Types.ObjectId, ref: 'reviews' }],
    // enrolledStudnets:{type:Number , default:0},
    //review:String,

},{timestamps:true}
)

const courseModel = model("Courses",courseSchema)

export default courseModel