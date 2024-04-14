package com.example.zoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkerController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private SceneController sceneController;

    public SceneController getSceneController() {
        return sceneController;
    }

    @FXML
    private Label label0;

    public void initialize(UserInfo loggedInUser) {
        label0.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
    }

}