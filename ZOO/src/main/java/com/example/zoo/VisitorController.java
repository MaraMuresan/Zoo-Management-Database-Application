package com.example.zoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VisitorController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label label0;

    private SceneController sceneController;

    public SceneController getSceneController() {
        return sceneController;
    }

    public void initialize(UserInfo loggedInUser) {
        label0.setText("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
    }

}