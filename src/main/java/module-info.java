module com.example.flipper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.flipper to javafx.fxml;
    exports com.example.flipper;
}