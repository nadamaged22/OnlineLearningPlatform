package com.example.finalonlinelearning.Entities;

import org.bson.types.ObjectId;

public class Enrollment {
    private ObjectId _id; // Add _id field
    private ObjectId studentId;
    private ObjectId courseId;
    private String status;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getStudentId() {
        return studentId;
    }

    public void setStudentId(ObjectId studentId) {
        this.studentId = studentId;
    }

    public ObjectId getCourseId() {
        return courseId;
    }

    public void setCourseId(ObjectId courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"studentId\": \"" + (studentId != null ? studentId.toHexString() : null) + "\"," +
                "\"courseId\": \"" + (courseId != null ? courseId.toHexString() : null) + "\"," +
                "\"status\": \"" + status + "\"" +
                "}";
    }
}
