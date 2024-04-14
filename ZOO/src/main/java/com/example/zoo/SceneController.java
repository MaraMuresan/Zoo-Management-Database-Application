package com.example.zoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class SceneController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Default constructor (no-argument constructor)
    public SceneController() {
    }

    // Constructor that takes a Stage parameter
    public SceneController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void switchToHello(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        stage.setTitle("ZOO");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();
    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        stage.setTitle("HOME");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label err;

    @FXML
    public void onLogInClick(ActionEvent event) {
        // Implement the logic for handling the login button click
        String enteredUsername = username.getText(); // Get the entered username
        String enteredPassword = password.getText(); // Get the entered password

        if (LoginManager.login(enteredUsername, enteredPassword)) {
            // Login successful, switch scenes or perform other actions
            UserInfo loggedInUser = LoginManager.getLoggedInUser();
            switch (loggedInUser.getUserType()) {
                case VISITOR:
                    switchToVisitor(loggedInUser.getFirstName(), loggedInUser.getLastName());
                    break;
                case WORKER:
                    switchToWorker(loggedInUser.getFirstName(), loggedInUser.getLastName());
                    break;
                case INVALID:
                    // Display error message for unsuccessful login
                    err.setText("Invalid credentials!");
                    break;
            }
        }
    }

    @FXML
    private Label label0;

    @FXML
    public void switchToVisitor(String first_name, String last_name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("visitor.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            stage.setTitle("VISITOR");
            stage.setScene(new Scene(root, 700, 450));
            label0.setText("Welcome, " + first_name + " " + last_name);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switchToWorker(String first_name, String last_name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("worker.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            stage.setTitle("WORKER");
            stage.setScene(new Scene(root, 700, 450));
            label0.setText("Welcome, " + first_name + " " + last_name);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onButton1Click(ActionEvent event) {
        try {
            switchToEnvironmentManagement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToEnvironmentManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("environment.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage environmentStage = new Stage();
        environmentStage.setTitle("Environment Management");
        environmentStage.setScene(new Scene(root, 600, 400));

        // Get the controller and set the stage for it
        EnvironmentController environmentController = loader.getController();
        environmentController.setStage(environmentStage);

        // Show the new stage
        environmentStage.show();

        // Optionally, close the current stage
        stage.close();
    }

    @FXML
    public void onButton2Click(ActionEvent event) {
        try {
            switchToAnimalManagement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToAnimalManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("animal.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage animalStage = new Stage();
        animalStage.setTitle("Animal Management");
        animalStage.setScene(new Scene(root, 871, 659));

        // Get the controller and set the stage for it
        AnimalController animalController = loader.getController();
        animalController.setStage(animalStage);

        // Show the new stage
        animalStage.show();

        // Optionally, close the current stage
        stage.close();
    }

    @FXML
    public void onButton3Click(ActionEvent event) {
        try {
            switchToTicketManagement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToTicketManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticket.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage ticketStage = new Stage();
        ticketStage.setTitle("Ticket Management");
        ticketStage.setScene(new Scene(root, 646, 400));

        // Get the controller and set the stage for it
        TicketController ticketController = loader.getController();
        ticketController.setStage(ticketStage);

        // Show the new stage
        ticketStage.show();

        // Optionally, close the current stage
        stage.close();
    }

    @FXML
    public void onButton4Click(ActionEvent event) {
        try {
            switchToReviewManagement();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToReviewManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("review.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage reviewStage = new Stage();
        reviewStage.setTitle("Review Management");
        reviewStage.setScene(new Scene(root, 600, 400));

        // Get the controller and set the stage for it
        ReviewController reviewController = loader.getController();
        reviewController.setStage(reviewStage);

        // Show the new stage
        reviewStage.show();

        // Optionally, close the current stage
        stage.close();
    }




//    private Stack<Parent> previousScenes = new Stack<>();
//
//    // ... (other fields and methods)
//
//    @FXML
//    public void switchToPreviousScene() {
//        if (!previousScenes.isEmpty()) {
//            Parent previousScene = previousScenes.pop();
//            stage.getScene().setRoot(previousScene);
//        } else {
//            System.out.println("No previous scene found.");
//        }
//    }
//
//    // ... (other methods)
//
//    // This method is used to set the current scene as the previous scene
//    public void setPreviousScene() {
//        previousScenes.push(stage.getScene().getRoot());
//    }
}

