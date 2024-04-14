package com.example.zoo;
import java.util.Objects;


public class Food {
    private int fid;
    private String name;
    private String type;
    private int quantity;

    // Default constructor
    public Food() {
    }

    // Constructor with parameters excluding aid
    public Food(String name, String type, int quantity){
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    // Constructor with all parameters including aid
    public Food(int fid, String name, String type, int quantity) {
        this.fid = fid;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Food: " +
                "fid: " + fid +
                ", name: " + name +
                ", type: " + type +
                ", quantity: " + quantity;
    }
}
