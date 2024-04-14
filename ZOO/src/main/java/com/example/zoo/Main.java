package com.example.zoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

            // Create an instance of SceneController and pass the primaryStage
            SceneController sceneController = new SceneController(primaryStage);

            // Set the controller for the loaded FXML
            loader.setController(sceneController);
            Parent root = loader.load();

            Scene scene = new Scene(root, 400, 500);
            primaryStage.setTitle("ZOO");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}