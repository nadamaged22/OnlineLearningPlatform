import { Schema } from "mongoose";
import { model } from "mongoose";

const userSchema = new Schema({
    name:{type : String , required : true},
    email:{type : String ,  unique : true, lowercase:true,required : true},
    password:{type : String , required : true},
    affiliation:{type : String , required : true},
    bio:{type : String , required : true},
    yearsOfExperience: {type : Number , default : 0},
    role : {type : String , default: 'Studnet', enum : ['Admin','Instructor','Student']},
},{timestamps:true})

const userModel= model("Users",userSchema)

export default userModel