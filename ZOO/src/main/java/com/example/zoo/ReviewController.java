package com.example.zoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;


public class ReviewController {

    private Stage stage;
    private SceneController sceneController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    public SceneController getSceneController() {
//        return sceneController;
//    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private TextArea feedback;

    @FXML
    private Label l3;

    @FXML
    private void onButtonsendClick(ActionEvent event) {

        l3.setText("");
        String reviewText = feedback.getText();

        if (reviewText.length() > 100) {
            l3.setText("Too many characters!");
            return;
        }

        try {
            // Get the current visitor (you need to replace this with your logic)
            UserInfo loggedInUser = LoginManager.getLoggedInUser();
            int visitorId = loggedInUser.getId(); // Replace with your logic to get the visitor ID

            // Update the review in the database
            updateVisitorReview(visitorId, reviewText);

            feedback.setText("Review submitted successfully!");

        } catch (SQLException e) {
            // Handle the exception (display an error message or log it)
            e.printStackTrace();
        }
    }

    private void updateVisitorReview(int visitorId, String reviewText) throws SQLException {
        // Perform the database update here
        try (Connection connection = establishConnection()) {

            String query = "UPDATE visitor SET review = ? WHERE vid = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, reviewText);
                statement.setInt(2, visitorId);
                statement.executeUpdate();
            }
        }
    }

    private Connection establishConnection() throws SQLException {
        // Establish the database connection
        String jdbcUrl = "jdbc:postgresql://localhost:5432/ZOO";
        String username = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void onButtonbClick(ActionEvent event) {
//        UserInfo loggedInUser = LoginManager.getLoggedInUser();
//        if (loggedInUser != null) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("visitor.fxml"));
//                Parent root = loader.load();
//
//                VisitorController visitorController = loader.getController();
//                visitorController.initialize(loggedInUser);
//
//                Scene visitorScene = new Scene(root, 700, 450);
//                Stage sceneStage = new Stage();
//                sceneStage.setTitle("Visitor");
//                sceneStage.setScene(visitorScene);
//                visitorController.setStage(sceneStage);
//
//                // Get the reference to the SceneController
//                SceneController sceneController = visitorController.getSceneController();
//
//                // Invoke the method in SceneController
//                sceneController.switchToVisitor(loggedInUser.getFirstName(), loggedInUser.getLastName());
//                sceneStage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
