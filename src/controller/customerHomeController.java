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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class customerHomeController {

    @FXML
    private TextField editUsername;
    @FXML
    private TextField editFirstName;
    @FXML
    private TextField editLastName;
    @FXML
    private TextField editPassword;
    @FXML
    private Button customerLogoutButton;
    @FXML
    private TextField userEmailEdit;
    @FXML
    private Button editAccountButton;
    @FXML
    private Button submitAccEditButton;


/*
    @FXML
    void printUserDataButtonClicked(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        // Step 2
        User u = (User) stage.getUserData();
        // Step 3
        int accType = u.getAccountType();


    }

 */


    @FXML
    void customerLogoutButtonClicked(ActionEvent event) throws IOException {
        System.out.println("going back to login");
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("/styles/login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void editAccountButtonClicked(ActionEvent event) {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();


        if (u.getEmail().equals(userEmailEdit.getText())){
            editUsername.setText(u.getUserName());
            editFirstName.setText(u.getFirstName());
            editLastName.setText(u.getLastName());
            editPassword.setText(u.getPassword());
        }

        else{
            System.out.println("Incorrect email please try again");
        }

    }

    @FXML
    void submitAccEditButtonClicked(ActionEvent event) {

        User user = new User(editUsername.getText(), editFirstName.getText(), editLastName.getText(), userEmailEdit.getText(), editPassword.getText(), 0);
        User.updateUser(user);

        editPassword.clear();
        editLastName.clear();
        editFirstName.clear();
        editUsername.clear();
        userEmailEdit.clear();


    }

}

