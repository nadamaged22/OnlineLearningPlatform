//package com.example.finalonlinelearning.Entities;
//
//import javax.json.bind.annotation.JsonbCreator;
//import javax.json.bind.annotation.JsonbProperty;
//import java.util.List;
//
//public class Course {
//    private String name;
//    private String category;
//    private String capacity;
//    private String Status;
//    private int duration;
//    private int enrolledStudents;
//    private int rating;
//    private AddedBy AddedBY;
//    private String _id;
//    private List<Review> reviews;
//    private String createdAt;
//    private String updatedAt;
//    private Integer __v;
//
//
//    // Default constructor
//    public Course() {
//    }
//
//    // Constructors
//    @JsonbCreator
//    public Course(@JsonbProperty("name") String name,
//                  @JsonbProperty("category") String category,
//                  @JsonbProperty("capacity") String capacity,
//                  @JsonbProperty("status") String Status,
//                  @JsonbProperty("duration") int duration,
//                  @JsonbProperty("enrolledStudnets") int enrolledStudents,
//                  @JsonbProperty("AddedBY") AddedBy AddedBY,
//                  @JsonbProperty("_id") String _id,
//                  @JsonbProperty("rating") int rating,
//                  @JsonbProperty("createdAt") String createdAt,
//                  @JsonbProperty("updatedAt") String updatedAt,
//                  @JsonbProperty(" __v") Integer __v){
//        this.name = name;
//        this.category = category;
//        this.capacity = capacity;
//        this.Status = Status;
//        this.duration = duration;
//        this.enrolledStudents = enrolledStudents;
//        this.AddedBY = AddedBY;
//        this._id = _id;
//        this.rating=rating;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.__v = __v;
//    }
//
//    // Getters and setters
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getStatus() {
//        return Status;
//    }
//    public Integer get__v() {
//        return __v;
//    }
//
//    public void setName(Integer __v) {
//        this.__v = __v;
//    }
//    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public void setStatus(String Status) {
//        this.Status = Status;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }
//
//    public int getDuration() {
//        return duration;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;
//    }
//
//    public int getEnrolledStudents() {
//        return enrolledStudents;
//    }
//
//    public void setEnrolledStudents(int enrolledStudents) {
//        this.enrolledStudents = enrolledStudents;
//    }
//
//    public AddedBy getAddedBY() {
//        return AddedBY;
//    }
//
//    public void setAddedBY(AddedBy addedBY) {
//        this.AddedBY = addedBY;
//    }
//
//
//
//    public static class AddedBy {
//        private String id;
//        private String email;
//
//        public AddedBy() {
//        }
//
//        @JsonbCreator
//        public AddedBy(@JsonbProperty("id") String id,
//                       @JsonbProperty("email") String email) {
//            this.id = id;
//            this.email = email;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//    }
//}
package com.example.finalonlinelearning.Entities;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class Course {
    private String _id;
    private String name;
    private String category;
    private String capacity;
    private String status;
    private int duration;
    private int rating;
    private AddedBy addedBY;
    private List<Review> reviews; // Reviews as a list of Review objects
    private String createdAt;
    private String updatedAt;
    private Integer __v;

    // Default constructor
    public Course() {
    }

    // Constructor
    @JsonbCreator
    public Course(@JsonbProperty("_id") String _id,
                  @JsonbProperty("name") String name,
                  @JsonbProperty("category") String category,
                  @JsonbProperty("capacity") String capacity,
                  @JsonbProperty("status") String status,
                  @JsonbProperty("duration") int duration,
                  @JsonbProperty("rating") int rating,
                  @JsonbProperty("addedBY") AddedBy addedBY,
                  @JsonbProperty("reviews") List<Review> reviews,
                  @JsonbProperty("createdAt") String createdAt,
                  @JsonbProperty("updatedAt") String updatedAt,
                  @JsonbProperty("__v") Integer __v) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.status = status;
        this.duration = duration;
        this.rating = rating;
        this.addedBY = addedBY;
        this.reviews = reviews;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    // Getters and setters
    public String get_id() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public AddedBy getAddedBY() {
        return addedBY;
    }

    public void setAddedBY(AddedBy addedBY) {
        this.addedBY = addedBY;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    // Nested class AddedBy
    public static class AddedBy {
        private String id;
        private String email;

        public AddedBy() {
        }

        @JsonbCreator
        public AddedBy(@JsonbProperty("id") String id,
                       @JsonbProperty("email") String email) {
            this.id = id;
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // Nested class Review
    public static class Review {
        private int rating;
        private String review;
        private int averageRatings;
        private AddedTo addedTo;
        private AddedBy addedBY;

        public Review() {
        }

        @JsonbCreator
        public Review(@JsonbProperty("rating") int rating,
                      @JsonbProperty("review") String review,
                      @JsonbProperty("averageRatings") int averageRatings,
                      @JsonbProperty("addedTo") AddedTo addedTo,
                      @JsonbProperty("addedBY") AddedBy addedBY) {
            this.rating = rating;
            this.review = review;
            this.averageRatings = averageRatings;
            this.addedTo = addedTo;
            this.addedBY = addedBY;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public int getAverageRatings() {
            return averageRatings;
        }

        public void setAverageRatings(int averageRatings) {
            this.averageRatings = averageRatings;
        }

        public AddedTo getAddedTo() {
            return addedTo;
        }

        public void setAddedTo(AddedTo addedTo) {
            this.addedTo = addedTo;
        }

        public AddedBy getAddedBY() {
            return addedBY;
        }

        public void setAddedBY(AddedBy addedBY) {
            this.addedBY = addedBY;
        }
    }

    // Nested class AddedTo
    public static class AddedTo {
        private String id;

        public AddedTo() {
        }

        @JsonbCreator
        public AddedTo(@JsonbProperty("id") String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
