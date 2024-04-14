package com.example.zoo;

public class Country {
    private int cid;
    private String name;
    private String continent;
    private int avg_temperature;

    // Default constructor
    public Country() {
    }

    public Country(String name, String continent, int avg_temperature) {
        this.name = name;
        this.continent = continent;
        this.avg_temperature = avg_temperature;
    }

    public Country(int cid, String name, String continent, int avg_temperature) {
        this.cid = cid;
        this.name = name;
        this.continent = continent;
        this.avg_temperature = avg_temperature;
    }

        public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getAvg_temperature() {
        return avg_temperature;
    }

    public void setAvg_temperature(int avg_temperature) {
        this.avg_temperature = avg_temperature;
    }

    @Override
    public String toString() {
        return "Country: " +
                "cid: " + cid +
                ", name: " + name +
                ", continent: " + continent +
                ", avg temperature: " + avg_temperature;
    }
}