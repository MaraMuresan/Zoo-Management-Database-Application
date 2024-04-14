package com.example.zoo;


public class UserInfo {
    
    public enum user_type {
        VISITOR, WORKER, INVALID
    }

    private int id;
    private String first_name;
    private String last_name;
    private user_type user_type;

    public UserInfo(int id, String first_name, String last_name, user_type user_type) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
    }

    // Add getters for the fields
    public int getId() {
        return id;
    }
    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public user_type getUserType() {
        return user_type;
    }
}