package com.example.finalonlinelearning.Entities;

import java.util.List;

public class MicroserviceResponse {
    private String message;
    private List<User> users;
    private List<Course> courses;
    private User user;


    // Constructors
    public MicroserviceResponse() {
    }

    public MicroserviceResponse(String message, List<User> users, List<Course> courses, User user) {
        this.message = message;
        this.users = users;
        this.courses = courses;
        this.user = user;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


