package com.example.zoo;

import java.sql.*;

public class LoginManager {

    private static UserInfo loggedInUser;

    public static boolean login(String username, String password) {
        // Perform the login logic, set loggedInUser if successful
        loggedInUser = isValidLogin(username, password);
        return loggedInUser != null;
    }

    public static UserInfo getLoggedInUser() {
        return loggedInUser;
    }

    private static UserInfo isValidLogin(String enteredUsername, String enteredPassword) {
        String queryVisitor = "SELECT * FROM visitor WHERE username = ? AND password = ?";
        String queryWorker = "SELECT * FROM worker WHERE username = ? AND password = ?";

        try {
            Class.forName("org.postgresql.Driver");
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ZOO", "postgres", "postgres");
            // Check the visitor table
            try (PreparedStatement statementVisitor = connection.prepareStatement(queryVisitor)) {
                statementVisitor.setString(1, enteredUsername);
                statementVisitor.setString(2, enteredPassword);

                try (ResultSet resultSet = statementVisitor.executeQuery()) {
                    if (resultSet.next()) {
                        // User found in the visitor table
                        return new UserInfo(
                                resultSet.getInt("vid"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                UserInfo.user_type.VISITOR
                        );
                    }
                }
            }

            // Check the worker table
            try (PreparedStatement statementWorker = connection.prepareStatement(queryWorker)) {
                statementWorker.setString(1, enteredUsername);
                statementWorker.setString(2, enteredPassword);

                try (ResultSet resultSet = statementWorker.executeQuery()) {
                    if (resultSet.next()) {
                        // User found in the worker table
                        return new UserInfo(
                                resultSet.getInt("wid"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                UserInfo.user_type.WORKER
                        );
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return new UserInfo(0,"", "", UserInfo.user_type.INVALID);
    }

}
