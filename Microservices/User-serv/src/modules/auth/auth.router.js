import * as authController from './controller/auth.js'
import { Router } from 'express';
import { asyncHandler } from '../../utils/errorHandling.js';


const router = Router();

router.post("/signup",asyncHandler(authController.signup))
router.post("/signin",asyncHandler(authController.signin))


export default router