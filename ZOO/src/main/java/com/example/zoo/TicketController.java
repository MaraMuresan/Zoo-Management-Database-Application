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
import java.util.Arrays;
import java.util.List;

public class TicketController {

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
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Integer> tidColumn;

    @FXML
    private TableColumn<Ticket, String> categoryColumn;

    @FXML
    private TableColumn<Ticket, Integer> priceColumn;

    @FXML
    private TableColumn<Ticket, Integer> environmentColumn;

    @FXML
    private ComboBox<String> ticketComboBox1;

    @FXML
    private ComboBox<Environment> ticketComboBox2;

    private Connection connection;
    private t_vRepository repository;

    // This method is called by JavaFX when the controller is initialized
    @FXML
    public void initialize() {
        // Initialize the columns
        tidColumn.setCellValueFactory(new PropertyValueFactory<>("tid"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        environmentColumn.setCellValueFactory(new PropertyValueFactory<>("environment"));

        connection = establishConnection(); // Get your connection here
        repository = new t_vRepository(connection); // Create an instance of t_vRepository

        // Initialize the ComboBox with available categories (for selection)
        initializeTicketComboBox1();

        // Initialize the ComboBox with available environments (for selection)
        initializeTicketComboBox2();

        // Load environments for the default worker (you can change this based on your logic)
        UserInfo loggedInUser = LoginManager.getLoggedInUser();

        int defaultVisitorId = loggedInUser.getId(); // Replace with your logic to get the visitor ID

        loadTicketsForVisitor(defaultVisitorId);
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

    private void initializeTicketComboBox1() {
        try {
            List<String> ticketCategories = Arrays.asList("Adult", "Child", "Student");
            ObservableList<String> observableCategories = FXCollections.observableArrayList(ticketCategories);
            ticketComboBox1.setItems(observableCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeTicketComboBox2() {
        try {
            List<Environment> allEnvironments = repository.getAllEnvironments(); // Use repository method
            ObservableList<Environment> observableEnvironments = FXCollections.observableArrayList(allEnvironments);
            ticketComboBox2.setItems(observableEnvironments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTicketsForVisitor(int visitorId) {
        try {
            List<Ticket> tickets = repository.getTicketsForVisitor(visitorId); // Use repository method
            ObservableList<Ticket> observableTickets = FXCollections.observableArrayList(tickets);
            ticketTable.setItems(observableTickets);

            // Calculate total price after loading tickets
            calculateTotalPrice();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Label labelt;

    private void calculateTotalPrice() {
        double total = 0.0;

        for (Ticket ticket : ticketTable.getItems()) {
            total += ticket.getPrice();
        }

        // Update the label with the total price
        labelt.setText("Total Price: $" + total);
    }

    // Event handler for adding an environment for the selected worker
    @FXML
    public void onAddTicketClick() {
        // Get selected ticket from the combo box
        String selectedCategory = ticketComboBox1.getValue();

        // Get selected environment from the combo box
        Environment selectedEnvironment = ticketComboBox2.getValue();

        if (selectedCategory != null && selectedEnvironment != null) {
            try {
                // Find the corresponding ticket in the Ticket table based on category and environment
                Ticket matchingTicket = repository.getTicketByCategoryAndEnvironment(selectedCategory, selectedEnvironment.getEid());

                if (matchingTicket != null) {
                    // Assuming you have the visitor ID (replace with your logic)
                    UserInfo loggedInUser = LoginManager.getLoggedInUser();
                    int visitorId = loggedInUser.getId(); // Replace with your logic to get the visitor ID

                    // Add the ticket to the t_v table
                    repository.addTicketForVisitor(matchingTicket.getTid(), visitorId);

                    // Load and display updated tickets for the visitor
                    loadTicketsForVisitor(visitorId);
                } else {
                    // Handle the case where no matching ticket is found
                    System.out.println("No matching ticket found for the selected category and environment.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Event handler for deleting the selected environment for the worker
    @FXML
    public void onDeleteTicketClick() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            try {
                // Assuming you have the worker ID (replace with your logic)
                UserInfo loggedInUser = LoginManager.getLoggedInUser();
                int visitorId = loggedInUser.getId(); // Replace with your logic to get the worker ID
                repository.deleteTicketForVisitor(selectedTicket.getTid(), visitorId); // Use repository method
                loadTicketsForVisitor(visitorId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
