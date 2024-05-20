import ConnectDB from "../DB/connection.js";
import userRouter from './modules/user/user.router.js'
import authRouter from './modules/auth/auth.router.js'
import { globalErrorHandling } from "./utils/errorHandling.js";
import { createDefaultAdmin } from "./modules/auth/controller/auth.js";
import cors from 'cors';




const bootstrap = (app,express) =>{
    app.use(express.json())
    app.use(express.static('front')); // Serve static files from 'public' directory
    app.use(cors());
    app.use("/auth",authRouter)
    app.use("/user",userRouter)
    app.use("*",(req,res,next)=>{
        return res.json({message:"In-valid Routing"})
    })
    app.use(globalErrorHandling)
    createDefaultAdmin()
    ConnectDB()
}
export default bootstrap