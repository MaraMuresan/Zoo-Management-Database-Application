package com.example.zoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class t_vRepository {
    private Connection connection;  // Assume you have a database connection

    public t_vRepository(Connection connection) {
        this.connection = connection;
    }

    // Method to get all environments
    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        // Assuming 't_v' is the join table between 'Ticket' and 'Visitor'
        String query = "SELECT DISTINCT t.* FROM ticket t " +
                "JOIN t_v tv ON t.tid = tv.ticket";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int tid = resultSet.getInt("tid");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                int environment = resultSet.getInt("environment");

                Ticket ticket = new Ticket(tid, category, price, environment);
                tickets.add(ticket);
            }
        }

        return tickets;
    }

    // Method to get environments for a specific worker
    public List<Ticket> getTicketsForVisitor(int visitorId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        // Assuming 't_v' is the join table between 'Visitor' and 'Ticket'
        String query = "SELECT t.* FROM ticket t " +
                "JOIN t_v tv ON t.tid = tv.ticket " +
                "WHERE tv.visitor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, visitorId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int tid = resultSet.getInt("tid");
                    String category = resultSet.getString("category");
                    int price = resultSet.getInt("price");
                    int environment = resultSet.getInt("environment");

                    Ticket ticket = new Ticket(tid, category, price, environment);
                    tickets.add(ticket);
                }
            }
        }

        return tickets;
    }

    public Ticket getTicketByCategoryAndEnvironment(String category, int environmentId) throws SQLException {

        String query = "SELECT * FROM ticket WHERE category = ? AND environment = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category);
            statement.setInt(2, environmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int tid = resultSet.getInt("tid");
                    int price = resultSet.getInt("price");
                    int environment = resultSet.getInt("environment");

                    return new Ticket(tid, category, price, environment);
                }
            }
        }

        return null; // Return null if no matching ticket is found
    }

    public void addTicketForVisitor(int ticket, int visitor) throws SQLException {

        String query = "INSERT INTO t_v(ticket, visitor) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket);
            statement.setInt(2, visitor);

            statement.executeUpdate();
        }
    }

    public void deleteTicketForVisitor(int ticket, int visitor) throws SQLException {
        String query = "DELETE FROM t_v WHERE ticket = ? AND visitor = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket);
            statement.setInt(2, visitor);

            statement.executeUpdate();
        }
    }

    public List<Environment> getAllEnvironments() throws SQLException {
        List<Environment> environments = new ArrayList<>();

        // Assuming 'e-w' is the join table between 'Worker' and 'Environment'
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

}
