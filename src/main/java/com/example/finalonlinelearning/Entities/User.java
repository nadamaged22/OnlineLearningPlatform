package com.example.finalonlinelearning.Entities;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String name;
    private String email;
    private String password;
    private String affiliation;
    private Integer yearsOfExperience;
    private String bio;
    private String roles;
    private String _id;
    private String createdAt;
    private String updatedAt;
    private Integer __v;

    // Default constructor
    public User() {
    }

    // Constructors
    @JsonbCreator
    public User(@JsonbProperty("name") String name,
                @JsonbProperty("email") String email,
                @JsonbProperty("password") String password,
                @JsonbProperty("affiliation") String affiliation,
                @JsonbProperty("yearsOfExperience") Integer yearsOfExp,
                @JsonbProperty("bio") String bio,
                @JsonbProperty("role") String roles,
                @JsonbProperty("_id") String _id,
                @JsonbProperty("createdAt") String createdAt,
                @JsonbProperty("updatedAt") String updatedAt,
                @JsonbProperty(" __v") Integer __v) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.yearsOfExperience = yearsOfExp;
        this.bio = bio;
        this.roles = roles;
        this._id = _id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer get__v() {
        return __v;
    }

    public void setName(Integer __v) {
        this.__v = __v;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @JsonbProperty("yearsOfExperience")
    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    @JsonbProperty("yearsOfExperience")
    public void setYearsOfExperience(Integer yearsOfExp) {
        this.yearsOfExperience = yearsOfExp;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRole() {
        return roles;
    }

    public void setRole(String roles) {
        this.roles = roles;
    }

    // Getters and setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
}

