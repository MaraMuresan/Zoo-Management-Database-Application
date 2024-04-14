module com.example.zoo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.zoo to javafx.fxml;
    exports com.example.zoo;

    requires java.sql;
}