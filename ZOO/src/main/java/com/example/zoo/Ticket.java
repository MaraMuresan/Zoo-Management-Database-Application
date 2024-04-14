package com.example.zoo;

public class Ticket {
    private int tid;
    private String category;
    private int price;
    private int environment;

    // Default constructor
    public Ticket() {
    }

    public Ticket(String category, int price, int environment) {
        this.category = category;
        this.price = price;
        this.environment = environment;
    }

    public Ticket(int tid, String category, int price, int environment) {
        this.tid = tid;
        this.category = category;
        this.price = price;
        this.environment = environment;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int cid) {
        this.tid = tid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEnvironment() {
        return environment;
    }

    public void setEnvironment(int environment) {
        this.environment = environment;
    }


    @Override
    public String toString() {
        return category; // Return the name as the string representation
    }
}