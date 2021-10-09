package controller;

import helpers.DBConnection;
import helpers.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class adminHomeController {

    /**********************************WE SHALL PUT ALL OUR WORK FROM EACH ADMIN TAB IN HERE**************************/
    /**************************ADD ACCOUNT************************************************/

    //Instance Variable
    User currentUser; // not 100% sure on this. Gotta see what its purpose is overall.


    //FXML Variables
    @FXML
    private Button createAdminButton;
    @FXML
    private TextField createUserAdmin;
    @FXML
    private TextField createPassAdmin;
    @FXML
    private TextField createFirstNAdmin;
    @FXML
    private TextField createLastNAdmin;
    @FXML
    private TextField createEmailAdmin;
    @FXML
    private Text outputForButton;

    @FXML
    public void createAdminButtonClicked(Event e) throws IOException {
        System.out.println("Create Admin Button");

        //checks for empty fields
        if (createUserAdmin.getText().isEmpty() || createPassAdmin.getText().isEmpty()) { //create if statement for each textbox
            System.out.println("Please enter username and password");
        }
        else {
            currentUser = new User(createUserAdmin.getText(), createFirstNAdmin.getText(), createLastNAdmin.getText(), createEmailAdmin.getText(), createPassAdmin.getText(), 1);

            //Launching DataBase Instance
            Connection connectDB = DBConnection.getConnection(); //Below is it hotel_db.Admin?
            String insertQuery = "INSERT INTO hotel_db.User (username, firstname, lastname, email, password, accType) VALUES (?, ?, ?, ?, ?, 1)";

            try {
                //Create statement, fill empty fields
                PreparedStatement statement = connectDB.prepareStatement(insertQuery);
                statement.setString(1,currentUser.getUserName());
                statement.setString(2,currentUser.getFirstName());
                statement.setString(3,currentUser.getLastName());
                statement.setString(4,currentUser.getEmail());
                statement.setString(5,currentUser.getPassword());
                statement.executeUpdate();

                System.out.println("Success Inserted " + currentUser.toString());

               // outputForButton.setText("Successful Admin Creation"); //maybe?

                //Clear ALL text fields
                createUserAdmin.clear();
                createPassAdmin.clear();
                createFirstNAdmin.clear();
                createLastNAdmin.clear();
                createEmailAdmin.clear();
            }
            catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println("Username taken! Try another one");
            }
            catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error: Failed to insert to DB");
            }
        }
    }

    /**************************************************************************/

    /************************LOGOUT TAB******************************************/
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
    /************************************************************************/

}