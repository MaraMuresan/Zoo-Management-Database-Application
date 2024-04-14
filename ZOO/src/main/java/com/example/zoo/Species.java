package com.example.zoo;

public class Species {
    private int sid;
    private String name;
    private int life_expectancy;
    private String country;

    // Default constructor
    public Species() {
    }

    public Species(String name, int life_expectancy, String country) {
        this.name = name;
        this.life_expectancy = life_expectancy;
        this.country = country;
    }

    public Species(int sid, String name, int life_expectancy, String country) {
        this.sid = sid;
        this.name = name;
        this.life_expectancy = life_expectancy;
        this.country = country;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifeExpectancy() {
        return life_expectancy;
    }

    public void setLifeExpectancy(int life_expectancy) {
        this.life_expectancy = life_expectancy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Species: " +
                "sid: " + sid +
                ", name: " + name +
                ", life expectancy: " + life_expectancy +
                ", country: " + country;
    }
}