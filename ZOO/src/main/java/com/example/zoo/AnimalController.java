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
import java.util.Arrays;

public class AnimalController {

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
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, Integer> aidColumn;

    @FXML
    private TableColumn<Animal, String> nameColumn;

    @FXML
    private TableColumn<Animal, Integer> ageColumn;

    @FXML
    private TableColumn<Animal, String> colorColumn;

    @FXML
    private TableColumn<Animal, Integer> weightColumn;

    @FXML
    private TableColumn<Animal, Integer> speciesColumn;

    @FXML
    private TableColumn<Animal, Integer> environmentColumn;

    @FXML
    private ComboBox<String> animalComboBox;

    private Connection connection;
    private animalRepository repository;

    // This method is called by JavaFX when the controller is initialized
    @FXML
    public void initialize() {
        // Initialize the columns
        aidColumn.setCellValueFactory(new PropertyValueFactory<>("aid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        environmentColumn.setCellValueFactory(new PropertyValueFactory<>("environment"));

        connection = establishConnection(); // Get your connection here
        repository = new animalRepository(connection); // Create an instance of e_wRepository

        // Initialize the ComboBox with available environments (for selection)
        initializeAnimalComboBox();
        // Load environments for the default worker (you can change this based on your logic)
        UserInfo loggedInUser = LoginManager.getLoggedInUser();

        int defaultWorkerId = loggedInUser.getId(); // Replace with your logic to get the worker ID

        loadAnimalsForWorker(defaultWorkerId);
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
    private void initializeAnimalComboBox() {
        try {
            List<String> animalCategories = Arrays.asList("environment", "food", "species");
            ObservableList<String> observableCategories = FXCollections.observableArrayList(animalCategories);
            animalComboBox.setItems(observableCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAnimalsForWorker(int workerId) {
        try {
            List<Animal> animals = repository.getAnimalsForWorker(workerId); // Use repository method
            ObservableList<Animal> observableAnimals = FXCollections.observableArrayList(animals);
            animalTable.setItems(observableAnimals);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Label labela, labelb;

    @FXML
    public void onButtonSeeClick() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();

        if (selectedAnimal != null) {
            // Check if the selected item is not null
            String selectedCategory = animalComboBox.getValue();

            switch (selectedCategory) {
                case "species":
                    try {
                        labelb.setText("");
                        Species species = repository.getSpeciesForAnimal(selectedAnimal.getAid());
                        Country country = repository.getCountryForAnimal(selectedAnimal.getAid());

                        String ok1, ok2;

                        if (species.getLifeExpectancy() == 0) ok1 = "--";
                        else ok1 = String.valueOf(species.getLifeExpectancy());

                        if (country.getAvg_temperature() == 0) ok2 = "--";
                        else ok2 = String.valueOf(country.getAvg_temperature());

                        if (species != null && country != null) {
                            // Update the label or perform other actions
                            String speciesInfo = "Species: " + species.getName() + "\n" + "\n" +
                                                 "Life Expectancy: " + ok1+ "\n" + "\n" +
                                                 "Country: " + species.getCountry();
                            labela.setText(speciesInfo);

                            String countryInfo = "Country: " + country.getName() + "\n" + "\n" +
                                                 "Continent: " + country.getContinent() + "\n" + "\n" +
                                                 "Avg Temperature: " + ok2;
                            labelb.setText((countryInfo));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "environment":
                    try {
                        labelb.setText("");
                        Environment environment = repository.getEnvironmentForAnimal(selectedAnimal.getAid());

                        String ok;

                        if (environment.getSurface() == 0) ok = "--";
                        else ok = String.valueOf(environment.getSurface());

                        if (environment != null) {
                            // Update the label or perform other actions
                            String EnvironmentInfo = "Environment: " + environment.getName() + "\n" + "\n" +
                                                     "Type: " + environment.getType() + "\n" + "\n" +
                                                     "Surface: " + ok;
                            labela.setText(EnvironmentInfo);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "food":
                    try {
                        labelb.setText("");
                        List<Food> foods = repository.getFoodForAnimal(selectedAnimal.getAid());

                        if (foods != null && !foods.isEmpty()) {
                            // Update the label or perform other actions
                            StringBuilder foodInfo = new StringBuilder();
                            for (Food food : foods) {
                                String ok = (food.getQuantity() == 0) ? "--" : String.valueOf(food.getQuantity());
                                foodInfo.append("Food: ").append(food.getName()).append("\n")
                                        .append("Type: ").append(food.getType()).append("\n")
                                        .append("Quantity: ").append(ok).append("\n \n");
                            }
                            labela.setText(foodInfo.toString());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

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
//                sceneController.switchToWorker(loggedInUser.getFirstName(), loggedInUser.getLastName());
//                sceneStage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

}
