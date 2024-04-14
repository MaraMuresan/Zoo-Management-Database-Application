package com.example.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class e_wRepository {
    private Connection connection;  // Assume you have a database connection

    public e_wRepository(Connection connection) {
        this.connection = connection;
    }

    // Method to get all environments
    public List<Environment> getAllEnvironments() throws SQLException {
        List<Environment> environments = new ArrayList<>();

        //'e-w' is the join table between 'Worker' and 'Environment'
        String query = "SELECT DISTINCT e.* FROM environment e " +
                "JOIN e_w ew ON e.eid = ew.environment";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int eid = resultSet.getInt("eid");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                int surface = resultSet.getInt("surface");

                Environment environment = new Environment(eid, name, type, surface);
                environments.add(environment);
            }
        }

        return environments;
    }

    // Method to get environments for a specific worker
    public List<Environment> getEnvironmentsForWorker(int workerId) throws SQLException {
        List<Environment> environments = new ArrayList<>();


        String query = "SELECT e.* FROM environment e " +
                "JOIN e_w ew ON e.eid = ew.environment " +
                "WHERE ew.worker = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, workerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int eid = resultSet.getInt("eid");
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    int surface = resultSet.getInt("surface");

                    Environment environment = new Environment(eid, name, type, surface);
                    environments.add(environment);
                }
            }
        }

        return environments;
    }

    public void addEnvironmentForWorker(int environment, int worker) throws SQLException {

        String query = "INSERT INTO e_w (environment, worker) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, environment);
            statement.setInt(2, worker);

            statement.executeUpdate();
        }
    }

    public void deleteEnvironmentForWorker(int environment, int worker) throws SQLException {

        String query = "DELETE FROM e_w WHERE environment = ? AND worker = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, environment);
            statement.setInt(2, worker);

            statement.executeUpdate();
        }
    }

}
