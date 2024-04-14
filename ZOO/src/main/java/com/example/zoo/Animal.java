package com.example.zoo;
import java.util.Objects;


public class Animal {
    private int aid;
    private String name;
    private int age;
    private String color;
    private int weight;
    private int species;
    private int environment;

    // Default constructor
    public Animal() {
    }

    // Constructor with parameters excluding aid
    public Animal(String name, int age, String color, int weight, int species, int environment){
        this.name = name;
        this.age = age;
        this.color = color;
        this.weight = weight;
        this.species = species;
        this.environment = environment;
    }

    // Constructor with all parameters including aid
    public Animal(int aid, String name, int age, String color, int weight, int species, int environment) {
        this.aid = aid;
        this.name = name;
        this.age = age;
        this.color = color;
        this.weight = weight;
        this.species = species;
        this.environment = environment;
    }

    // Getters and setters
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }

    public int getEnvironment() {
        return environment;
    }

    public void setEnvironment(int environment) {
        this.environment = environment;
    }
}
