package com.example.finalonlinelearning.Entities;

public class MicroServiceCount {
    private String message;
    private int usersCount;
    private int studentsCount;
    private int instructorsCount;
    private int coursesCount;

    // Constructors
    public MicroServiceCount() {
    }

    public MicroServiceCount(int usersCount, int studentsCount, int instructorsCount, int coursesCount) {
        this.usersCount = usersCount;
        this.studentsCount = studentsCount;
        this.instructorsCount = instructorsCount;
        this.coursesCount = coursesCount;
    }

    // Getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getInstructorsCount() {
        return instructorsCount;
    }

    public void setInstructorsCount(int instructorsCount) {
        this.instructorsCount = instructorsCount;
    }
        public int getCoursesCount() {
        return coursesCount;
    }

    public void setCoursesCount(int coursesCount) {
        this.coursesCount = coursesCount;
    }

}

//    private String message;
//    private int usersCount;
//    private int studentsCount;
//    private int instructorsCount;
//    private int coursesCount;
//
//    // Constructors
//    public MicroServiceCount() {
//    }
//
//    public MicroServiceCount(String message, int usersCount, int studentsCount, int instructorsCount, int coursesCount) {
//        this.message = message;
//        this.usersCount = usersCount;
//        this.studentsCount = studentsCount;
//        this.instructorsCount = instructorsCount;
//        this.coursesCount = coursesCount;
//    }
//
//    // Getters and setters
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public int getUsersCount() {
//        return usersCount;
//    }
//
//    public void setUsersCount(int usersCount) {
//        this.usersCount = usersCount;
//    }
//
//    public int getStudentsCount() {
//        return studentsCount;
//    }
//
//    public void setStudentsCount(int studentsCount) {
//        this.studentsCount = studentsCount;
//    }
//
//    public int getInstructorsCount() {
//        return instructorsCount;
//    }
//
//    public void setInstructorsCount(int instructorsCount) {
//        this.instructorsCount = instructorsCount;
//    }
//

