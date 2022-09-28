module com.example.neural_networks {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.neural_networks to javafx.fxml;
    exports com.example.neural_networks;
}