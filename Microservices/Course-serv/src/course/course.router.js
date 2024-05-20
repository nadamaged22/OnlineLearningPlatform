import { Router } from 'express'
import * as courseController from './controller/course.js'
import { asyncHandler } from '../utils/errorHandling.js';


const router = Router();

router.post("/addcourse",asyncHandler(courseController.addCourse))
router.get("/viewcourse/:courseId",asyncHandler(courseController.ViewCourseInfo))
router.get("/searchname",asyncHandler(courseController.SearchByName))
router.get("/searchcat",asyncHandler(courseController.SearchByCategory))
router.get("/searchrate",asyncHandler(courseController.SearchByRating))
router.put("/updateCourse/:courseId",asyncHandler(courseController.updateCourse))
router.put("/updateStatus/:id",asyncHandler(courseController.updateCourseStatus))
router.delete("/deletecourse/:id",asyncHandler(courseController.deleteCourse))
router.get("/getall",asyncHandler(courseController.getAllCourses))
router.get("/getPending",asyncHandler(courseController.getPendingCourses))

export default router 



