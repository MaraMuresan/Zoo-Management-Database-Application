package com.example.zoo;
import java.util.Objects;


public class Environment {
    private int eid;
    private String name;
    private String type;
    private int surface;

    // Default constructor
    public Environment() {
    }

    // Constructor with parameters excluding eid
    public Environment(String name, String type, int surface) {
        this.name = name;
        this.type = type;
        this.surface = surface;
    }

    // Constructor with all parameters including eid
    public Environment(int eid, String name, String type, int surface) {
        this.eid = eid;
        this.name = name;
        this.type = type;
        this.surface = surface;
    }

    // Getters and setters
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return name; // Return the name as the string representation
    }
}
