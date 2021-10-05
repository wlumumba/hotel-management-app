package controller;

import helpers.DBConnection;
import helpers.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class loginController {

    //Instance variables
    User currentUser;

    //FXML Variables
    @FXML
    private TextField userNameLog;

    @FXML
    private TextField passwordLog;

    @FXML
    private Button customerButton;

    @FXML
    private Button createButton;

    @FXML
    private Button guestButton;

    @FXML
    private TextField userNameCreate;

    @FXML
    private TextField passwordCreate;

    @FXML
    private TextField firstNameCreate;

    @FXML
    private TextField lastNameCreate;

    @FXML
    private TextField emailCreate;


    @FXML
    public void customerButtonClicked(Event e) throws IOException {
        System.out.println("customer/admin login");
        Parent newHome;
        Scene newScene;

        // Pull username/pass
        String usernameCheck = userNameLog.getText();
        String passwordCheck = passwordLog.getText();
        userNameLog.clear();
        passwordLog.clear();

        //Launch DB instance
        Connection connectDB = DBConnection.getConnection();
        String selectQuery = "SELECT * FROM hotel_db.User where username = ?  and password = ?";

        try {
            //Create statement, fill empty fields
            PreparedStatement statement = connectDB.prepareStatement(selectQuery);
            statement.setString(1, usernameCheck);
            statement.setString(2, passwordCheck);

            //Store result rows
            ResultSet rs = statement.executeQuery();

            // RS should only contain 1 row, or NO ROW matched
            if(rs.next())
            {
                // Fill User object
                currentUser = new User(rs.getString("username"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("password"), rs.getInt("accType"));

                // IF Customer: Switch to customer view
                if(currentUser.getAccountType() == 0)
                {
                    //Loading in customerHome.fxml
                    newHome = FXMLLoader.load(getClass().getResource("/styles/customerHome.fxml"));
                    newScene = new Scene(newHome);

                }
                // IF Admin: Switch to admin view
                else
                {
                    //Loading in adminHome.fxml
                    newHome = FXMLLoader.load(getClass().getResource("/styles/adminHome.fxml"));
                    newScene = new Scene(newHome);
                }

                //Obtaining stage information and setting our new scene/fxml
                Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                currentStage.setScene(newScene);
                currentStage.setUserData(currentUser); // PASSING USER TO NEXT SCENE
                currentStage.show();

            }
            else
                throw new EOFException("No row matched: incorrect login");

        }
        // Catch block for incorrect login
        catch (EOFException ex){
            System.out.println(ex.getMessage());
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: Failed to query database for that input");
        }
    }

    @FXML
    public void createButtonClicked(Event e) throws IOException {
        System.out.println("create login button");

        //Check for empty fields
        if(userNameCreate.getText().isEmpty() || passwordCreate.getText().isEmpty())
           System.out.println("Please enter password and username");
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

                //Clear ALL text fields
                userNameCreate.clear();
                passwordCreate.clear();
                firstNameCreate.clear();
                lastNameCreate.clear();
                emailCreate.clear();
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

    @FXML
    public void guestButtonClicked(Event e) throws IOException {

        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("/styles/guestHome.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}