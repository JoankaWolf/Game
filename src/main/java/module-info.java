module com.kodilla.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kodilla.game to javafx.fxml;
    exports com.kodilla.game;
}