import jwt from 'jsonwebtoken'
import { asyncHandler } from '../utils/errorHandling.js'
import userModel from '../../DB/model/user.model.js'
import AdminModel from '../../DB/model/admin.model.js'

export const roles = {
    admin: 'Admin',
    student:'Student',
    instructor:'Instructor'
}
Object.freeze(roles)

export const auth = (requiredRoles = []) => {
    return async (req, res, next) => {
        try {
            const { authorization } = req.headers;
            if (!authorization) {
                return res.status(401).json({ message: "No token provided" });
            }
            const token = authorization; // Splitting 'Bearer token'
            try {
                const decoded = jwt.verify(token, "sarahagfuisg1654685sarahagfuisg1654685sarahagfuisg1654685")
                if (!decoded?.id) {
                    return res.json({ message: "Invalid token payload" })
                }
                let user;
                if (requiredRoles.includes(roles.admin)) {
                    user = await AdminModel.findById(decoded.id).select('-password');
                } else if(requiredRoles.includes(roles.student)||requiredRoles.includes(roles.instructor)) {
                    user = await userModel.findById(decoded.id).select('-password');
                }
                if (!user) {
                    return res.status(401).json({ message: 'User not Authorized' });
                }
                req.user = user;
                return next()
            } catch (error) {
                if (error.name === 'TokenExpiredError') {
                    return res.status(401).json({ message: 'Token expired' });
                }
                return res.status(401).json({ message: 'Invalid token' });
            }
        } catch (error) {
            return res.json({ message: "Catch error", err: error?.message })
        }
    }
}
