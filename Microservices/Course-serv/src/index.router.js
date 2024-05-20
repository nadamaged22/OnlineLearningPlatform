import ConnectDB from "../DB/connection.js"
import courseRouter from './course/course.router.js'
import reviewRouter from './review/review.router.js'
import { globalErrorHandling } from "./utils/errorHandling.js"
import cors from 'cors';

const bootstrap = (app,express)=>{
    app.use(express.json())
    app.use(cors());
    app.use("/course",courseRouter)
    app.use("/review",reviewRouter)
    app.use("*",(req,res,next)=>
        {
            return res.json({message:"In-valid Routing"})
        })
        app.use(globalErrorHandling)
        ConnectDB()
}
export default bootstrap    