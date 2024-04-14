package com.example.zoo;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;

public class EnvironmentController {

    private Stage stage;
    private SceneController sceneController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private TableView<Environment> environmentTable;

    @FXML
    private TableColumn<Environment, Integer> eidColumn;

    @FXML
    private TableColumn<Environment, String> nameColumn;

    @FXML
    private TableColumn<Environment, String> typeColumn;

    @FXML
    private TableColumn<Environment, Integer> surfaceColumn;

    @FXML
    private ComboBox<Environment> environmentComboBox;

    private Connection connection;
    private e_wRepository repository;

    // This method is called by JavaFX when the controller is initialized
    @FXML
    public void initialize() {
        // Initialize the columns
        eidColumn.setCellValueFactory(new PropertyValueFactory<>("eid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        surfaceColumn.setCellValueFactory(new PropertyValueFactory<>("surface"));

        connection = establishConnection(); // Get your connection here
        repository = new e_wRepository(connection); // Create an instance of e_wRepository

        // Initialize the ComboBox with available environments (for selection)
        initializeEnvironmentComboBox();
        // Load environments for the default worker (you can change this based on your logic)
        UserInfo loggedInUser = LoginManager.getLoggedInUser();

        int defaultWorkerId = loggedInUser.getId(); // Replace with your logic to get the worker ID

        loadEnvironmentsForWorker(defaultWorkerId);
    }

    private Connection establishConnection() {
            try {
                // JDBC URL, username, and password of MySQL server
                String jdbcUrl = "jdbc:postgresql://localhost:5432/ZOO";
                String username = "postgres";
                String password = "postgres";

                // Establish the connection
                return DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to the database");
            }
    }
    private void initializeEnvironmentComboBox() {
        try {
            List<Environment> allEnvironments = repository.getAllEnvironments(); // Use repository method
            ObservableList<Environment> observableEnvironments = FXCollections.observableArrayList(allEnvironments);
            environmentComboBox.setItems(observableEnvironments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEnvironmentsForWorker(int workerId) {
        try {
            List<Environment> environments = repository.getEnvironmentsForWorker(workerId); // Use repository method
            ObservableList<Environment> observableEnvironments = FXCollections.observableArrayList(environments);
            environmentTable.setItems(observableEnvironments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Event handler for adding an environment for the selected worker
    @FXML
    public void onAddEnvironmentClick() {
        Environment selectedEnvironment = environmentComboBox.getValue();
        if (selectedEnvironment != null) {
            try {
                // Assuming you have the worker ID (replace with your logic)
                UserInfo loggedInUser = LoginManager.getLoggedInUser();
                int workerId = loggedInUser.getId(); // Replace with your logic to get the worker ID
                repository.addEnvironmentForWorker(selectedEnvironment.getEid(), workerId); // Use repository method
                loadEnvironmentsForWorker(workerId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Event handler for deleting the selected environment for the worker
    @FXML
    public void onDeleteEnvironmentClick() {
        Environment selectedEnvironment = environmentTable.getSelectionModel().getSelectedItem();
        if (selectedEnvironment != null) {
            try {
                // Assuming you have the worker ID (replace with your logic)
                UserInfo loggedInUser = LoginManager.getLoggedInUser();
                int workerId = loggedInUser.getId(); // Replace with your logic to get the worker ID
                repository.deleteEnvironmentForWorker(selectedEnvironment.getEid(), workerId); // Use repository method
                loadEnvironmentsForWorker(workerId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




//    @FXML
//    public void switchToPreviousScene() {
//        if (sceneController != null) {
//            sceneController.switchToPreviousScene();
//        } else {
//            System.out.println("No scene controller found.");
//        }
//    }
//
//    // Call this method before switching scenes to set the current scene as the previous scene
//    private void setPreviousScene() {
//        if (sceneController != null) {
//            sceneController.switchToPreviousScene();
//        }
//    }
//
//        // ... (other methods)
//
//        // Example usage in a method that switches scenes
//        @FXML
//        public void onButtonbClick () {
//            setPreviousScene(); // Call this before switching scenes
//
//            UserInfo loggedInUser = LoginManager.getLoggedInUser();
//
//            // Assuming you have the logic to switch to the previous scene or worker scene
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("worker.fxml"));
//                loader.setController(this);
//                Parent root = loader.load();
//
//                WorkerController workerController = loader.getController();
//                workerController.initialize(loggedInUser);
//
//                Scene workerScene = new Scene(root, 700, 450);
//                Stage sceneStage = new Stage();
//                sceneStage.setTitle("WORKER");
//                sceneStage.setScene(workerScene);
//                workerController.setStage(sceneStage);
//
//                // Get the reference to the SceneController
//                SceneController sceneController = workerController.getSceneController();
//
//                // Invoke the method in SceneController
//                sceneController.switchToWorker(loggedInUser.getFirstName(), loggedInUser.getLastName());
//                sceneStage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//    @FXML
//    Label label0;

    @FXML
  public void onButtonbClick(ActionEvent event) {
//        UserInfo loggedInUser = LoginManager.getLoggedInUser();
//        if (loggedInUser != null) {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("worker.fxml"));
//                Parent root = loader.load();
//
//                WorkerController workerController = loader.getController();
//                workerController.initialize(loggedInUser);
//
//                Scene workerScene = new Scene(root, 700, 450);
//                Stage sceneStage = new Stage();
//                sceneStage.setTitle("WORKER");
//                sceneStage.setScene(workerScene);
//                workerController.setStage(sceneStage);
//
//                // Get the reference to the SceneController
//                SceneController sceneController = workerController.getSceneController();
//
//                // Invoke the method in SceneController
//                 sceneController.switchToWorker(loggedInUser.getFirstName(), loggedInUser.getLastName());
//                 sceneStage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
     }

    }
