import { roles } from "../../../middleware/authentication.js";

export const Userendpoints={
    userCrud:[roles.student, roles.instructor],
}
export const endpoints={
    userCrud:[roles.admin]
}
