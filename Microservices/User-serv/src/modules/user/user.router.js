import * as userController from './controller/user.js'
import {auth} from '../../middleware/authentication.js'
import { Router} from 'express'
import { Userendpoints, endpoints } from './controller/user.endpoint.js';

const router = Router();

router.get('/',auth(Userendpoints.userCrud),userController.getUsers)
router.get('/Admin',auth(endpoints.userCrud),userController.getAdmin)
router.get('/getall',auth(endpoints.userCrud),userController.getAllUsers)
router.get('/getallStudents',auth(endpoints.userCrud),userController.getAllStudents)
router.get('/getallInstructors',auth(endpoints.userCrud),userController.getAllInstructors)
router.get('/getUserBYID/:id',auth(endpoints.userCrud),userController.getUserById)
export default router