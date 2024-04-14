package com.example.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class animalRepository {
    private Connection connection;  // Assume you have a database connection

    public animalRepository(Connection connection) {
        this.connection = connection;
    }


    // Method to get animals for a specific worker
    public List<Animal> getAnimalsForWorker(int workerId) throws SQLException {
        List<Animal> animals = new ArrayList<>();

        String query = "SELECT a.* FROM animal a " +
                "JOIN environment e ON e.eid = a.environment " +
                //"JOIN species s ON s.sid = a.species " +
               // "JOIN f_a f ON f.animal = a.aid " +
                "JOIN e_w ew ON e.eid = ew.environment " +
                "WHERE ew.worker = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, workerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int aid = resultSet.getInt("aid");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String color = resultSet.getString("color");
                    int weight = resultSet.getInt("weight");
                    int species = resultSet.getInt("species");
                    int envrionment = resultSet.getInt("environment");

                    Animal animal = new Animal(aid, name, age, color, weight, species, envrionment);
                    animals.add(animal);
                }
            }
        }

        return animals;
    }

    public Species getSpeciesForAnimal(int animalId) throws SQLException {
        String query = "SELECT s.* FROM species s " +
                "JOIN animal a ON a.species = s.sid " +
                "WHERE a.aid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animalId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int sid = resultSet.getInt("sid");
                    String name = resultSet.getString("name");
                    int life_expectancy = resultSet.getInt("life_expectancy");
                    String country = resultSet.getString("country");

                    return new Species(sid, name, life_expectancy, country);
                }
            }
        }
        return null; // Return null if no species information is found for the given animal
    }

    public Country getCountryForAnimal(int animalId) throws SQLException {

        String query = "SELECT c.* FROM country c " +
                "JOIN species s ON c.cid = s.country " +
                "JOIN animal a ON a.species = s.sid " +
                "WHERE a.aid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animalId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int cid = resultSet.getInt("cid");
                    String name = resultSet.getString("name");
                    String continent = resultSet.getString("continent");
                    int avg_temperature = resultSet.getInt("avg_temperature");

                    return new Country(cid, name, continent, avg_temperature);
                }
            }
        }
        return null; // Return null if no species information is found for the given animal
    }

    public Environment getEnvironmentForAnimal(int animalId) throws SQLException {
        String query = "SELECT e.* FROM environment e " +
                "JOIN animal a ON a.environment = e.eid " +
                "WHERE a.aid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animalId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int eid = resultSet.getInt("eid");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    int surface = resultSet.getInt("surface");

                    return new Environment(eid, name, type, surface);
                }
            }
        }
        return null; // Return null if no species information is found for the given animal
    }

    public List<Food> getFoodForAnimal(int animalId) throws SQLException {
        List<Food> foods = new ArrayList<>();

        String query = "SELECT f.* FROM food f " +
                "JOIN f_a fa ON fa.food = f.fid " +
                "JOIN animal a ON fa.animal = a.aid " +
                "WHERE a.aid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animalId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int fid = resultSet.getInt("fid");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    int quantity = resultSet.getInt("quantity");

                    Food food = new Food(fid, name, type, quantity);
                    foods.add(food);
                }
            }
        }
        return foods;
    }


}
