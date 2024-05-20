import userModel from "../../../../DB/model/user.model.js";

import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken'
import {
	ReasonPhrases,
	StatusCodes,
	getReasonPhrase,
	getStatusCode,
} from 'http-status-codes';
import defaultUser from "../../../../DB/model/admin.model.js";
import AdminModel from "../../../../DB/model/admin.model.js";

// Signup
export const signup = async (req,res,next)=>
{
        const {name,email,password,affiliation,bio,yearsOfExperience,role}= req.body;
        const checkUser = await userModel.findOne({email})
        if(checkUser)
        {
                //return res.json({message : "Email Exist"})
                return next(new Error("Email Exist", {cause : StatusCodes.CONFLICT}))
        }
        if (role === 'Instructor' && yearsOfExperience === 0) {
            return next(new Error("Instructors must have years of experience greater than zero", 
            {cause : StatusCodes.BAD_REQUEST}))
        }
         const hashPassword =  bcrypt.hashSync(password,9)
        const user = await userModel.create({name,email,affiliation,bio,yearsOfExperience,role ,password : hashPassword})
        return res.status(StatusCodes.CREATED).json({message: "Done" , user,status : getReasonPhrase(StatusCodes.CREATED)})
}

export const signin = async (req, res, next) => {
  const { email, password } = req.body;
  const checkAdmin = await AdminModel.findOne({ email, password });

  if (!checkAdmin) {
      const checkUser = await userModel.findOne({ email });

      if (!checkUser) {
          return next(new Error("Email Does not Exist", { cause: StatusCodes.NOT_FOUND }));
      }

      const match = bcrypt.compareSync(password, checkUser.password);

      if (!match) {
          return next(new Error("Invalid Login Data", { cause: 400 }));
      }

      const token = jwt.sign(
          { userName: checkUser.name, role: checkUser.role, id: checkUser._id, email: checkUser.email },
          "sarahagfuisg1654685sarahagfuisg1654685sarahagfuisg1654685",
          { expiresIn: "1d" }
      );

      return res.status(200).json({ message: "Logged in Successfully", token, role: checkUser.role });
  } else {
      const token = jwt.sign(
          { role: checkAdmin.role, id: checkAdmin._id, email: checkAdmin.email },
          "sarahagfuisg1654685sarahagfuisg1654685sarahagfuisg1654685",
          { expiresIn: "1d" }
      );

      return res.status(200).json({ message: "Welcome Admin!", token, role: "Admin" });
  }
};

export const createDefaultAdmin = async () => {
    try {
      const existingAdmin = await AdminModel.findOne({ email: "admin@example.com" });
  
      if (!existingAdmin) {
        const defaultUser = new AdminModel({
          email: "admin@example.com",
          password: "123456",
        });
  
        await defaultUser.save();
        console.log("Default admin created successfully");
      } else {
        console.log("Default admin already exists");
      }
    } catch (error) {
      console.error("Error creating default admin:", error);
    }
  };
  
