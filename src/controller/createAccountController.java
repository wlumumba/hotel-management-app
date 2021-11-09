package controller;

import helpers.DBConnection;
import helpers.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class createAccountController {
    //instance Var
    User currentUser;

    @FXML
    private TextField userNameCreate;

    @FXML
    private PasswordField passwordCreate;

    @FXML
    private Button createButton;

    @FXML
    private Label errorText;

    @FXML
    private Label successText;

    @FXML
    private TextField firstNameCreate;

    @FXML
    private TextField lastNameCreate;

    @FXML
    private TextField emailCreate;

    @FXML
    private Button returnButton;

    @FXML
    void createButtonClicked(ActionEvent e) throws IOException {
        errorText.setText(" ");
        successText.setText(" ");

        System.out.println("create login button");
        Alert a = new Alert(Alert.AlertType.NONE);
        boolean usernameTaken = false;

        //Check for empty fields
        if(userNameCreate.getText().isEmpty()) {
            System.out.println("Please enter Username");
            errorText.setText("Please enter Username");
        }
        else if (passwordCreate.getText().isEmpty()) {
            System.out.println("Please enter Password");
            errorText.setText("Please enter Password");
        }
        else if (firstNameCreate.getText().isEmpty()) {
            System.out.println("Please enter First Name");
            errorText.setText("Please enter First Name");
        }
        else if (lastNameCreate.getText().isEmpty()) {
            System.out.println("Please enter Last Name");
            errorText.setText("Please enter Last Name");
        }
        else if (emailCreate.getText().isEmpty()) {
            System.out.println("Please enter Email");
            errorText.setText("Please enter Email");
        }
        else {

            currentUser = new User(userNameCreate.getText(), firstNameCreate.getText(), lastNameCreate.getText(), emailCreate.getText(), passwordCreate.getText(), 0);

            //Launch DB instance
            Connection connectDB = DBConnection.getConnection();
            String insertQuery = "INSERT INTO hotel_db.User (username, firstname, lastname, email, password, accType) VALUES (?, ?, ?, ?, ?, 0)";

            try {
                //Create statement, fill empty fields
                PreparedStatement statement = connectDB.prepareStatement(insertQuery);
                statement.setString(1, currentUser.getUserName());
                statement.setString(2, currentUser.getFirstName());
                statement.setString(3, currentUser.getLastName());
                statement.setString(4, currentUser.getEmail());
                statement.setString(5, currentUser.getPassword());
                statement.executeUpdate();

                System.out.println("Success Inserted " + currentUser.toString());
                successText.setText("Account Created!");




                //Clear ALL text fields
                userNameCreate.clear();
                passwordCreate.clear();
                firstNameCreate.clear();
                lastNameCreate.clear();
                emailCreate.clear();
            }

            catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println("Username taken! Try another one");
                errorText.setText("Username Taken");



            }
            catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error: Failed to insert to DB");
                errorText.setText("Erro 4542234");

            }


        }

    }

    @FXML
    void returnButtonClicked(ActionEvent event) throws IOException {

        System.out.println("Going back to login");
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
