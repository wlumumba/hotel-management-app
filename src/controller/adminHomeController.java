package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class adminHomeController {

    @FXML
    private Button adminLogoutButton;

    @FXML
    void adminLogoutButtonClicked(ActionEvent event) throws IOException {

        System.out.println("going back to loggin");
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("/styles/login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}