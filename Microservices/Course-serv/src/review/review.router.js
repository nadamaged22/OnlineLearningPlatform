import { Router } from "express";
import * as reviewController from './controller/review.js'
import { asyncHandler } from "../utils/errorHandling.js";


const router=Router();
router.post("/addv/:courseId",asyncHandler(reviewController.addReviewAndUpdateAvgRating));

export default router 
