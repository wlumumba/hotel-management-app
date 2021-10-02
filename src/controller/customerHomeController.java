package controller;

import helpers.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class customerHomeController {

    @FXML
    private Button customerLogoutButton;

    @FXML
    private Button printUserDataButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;


    @FXML
    void printUserDataButtonClicked(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        // Step 2
        User u = (User) stage.getUserData();
        // Step 3
        String userName = u.getUserName();
        String password = u.getPassword();

        userNameLabel.setText(userName);
        passwordLabel.setText(password);


    }


    @FXML
    void customerLogoutButtonClicked(ActionEvent event) throws IOException {
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

