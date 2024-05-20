import { Schema } from "mongoose";
import { model } from "mongoose";

const reviewSchema= new Schema({
    rating : {type:Number , required:true},
    review :{type : String , min: 0.0,max: 5.0},
    averageratings :{type : Number, default:0},

    addedTo: {
        id: {type: Schema.Types.ObjectId, ref: 'Courses', required:true},
    },
    addedBY: {
        id: {type: Schema.Types.ObjectId},
        email: String,
    },
},{timestamps:true}
)
const reviewModel = model("reviews",reviewSchema)

export default reviewModel