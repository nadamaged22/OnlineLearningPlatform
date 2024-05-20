import AdminModel from "../../../../DB/model/admin.model.js";
import userModel from "../../../../DB/model/user.model.js";
import { verifyToken } from "../../../utils/GenerateAndVerifyToken.js";
import { asyncHandler } from "../../../utils/errorHandling.js";
import jwt from 'jsonwebtoken'
export const getUsers = asyncHandler(async(req,res)=>
{
    const users = await userModel.findById(req.user._id)
    return res.json({message : "Done",users})
})
export const getAdmin = asyncHandler(async(req,res)=>
{
    const users = await AdminModel.findById(req.user._id)
    return res.json({message : "Done",users})
})
export const getAllUsers = asyncHandler(async(req,res)=>
{
 
    const users = await userModel.find({})
    return res.json({message : "Done",users})
})
export const getAllStudents = asyncHandler(async (req, res) => {
    const users = await userModel.find({ role: "Student" });
    return res.json({ message: "Done", users });
});
export const getAllInstructors = asyncHandler(async (req, res) => {
    const users = await userModel.find({ role: "Instructor" });
    return res.json({ message: "Done", users });
});
export const getUserById = asyncHandler(async (req, res) => {
    const userId = req.params.id;

    const user = await userModel.findById(userId);
    if (user) {
        return res.json({ message: "Done", user });
    } else {
        // Return 404 status with an error message
        return res.status(404).json({ message: "User not found" });
    }
});
