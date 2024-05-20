import { Schema } from "mongoose";
import { model } from "mongoose";
const AdminSchema = new Schema(
    {
      email: { type: String, unique: true, lowercase: true, required: true },
      password: { type: String, required: true },
      role: { type: String, default: "Admin" },
    },
    { timestamps: true }
  );
  
  const AdminModel = model("Admin", AdminSchema);
  export default AdminModel