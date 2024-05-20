package com.example.finalonlinelearning.Entities;

import org.bson.types.ObjectId;

public class Notification {
    private ObjectId id;
    private ObjectId studentId;
    private String title;
    private String message;
    private String status;

    public Notification(ObjectId studentId, String title, String message, String status) {
        this.studentId = studentId;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    // Getters and setters
}
