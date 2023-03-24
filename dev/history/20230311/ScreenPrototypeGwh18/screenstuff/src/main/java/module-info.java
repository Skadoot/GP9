module com.example.screenstuff {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.screenstuff to javafx.fxml;
    exports com.example.screenstuff;
}