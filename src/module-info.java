module hotel.management.app {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens controller;
    opens helpers;
}