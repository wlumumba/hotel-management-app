package controller;

import helpers.DBConnection;
import helpers.Hotel;
import helpers.User;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class adminHomeController implements Initializable {
    //Instance Variable
    User currentUser;
    ObservableList<Hotel> hotelList;

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
    private Label labelAdminOuput;
    @FXML
    private Label errorAdminOutput;
    @FXML
    private TableColumn<Hotel, Integer> col_qprice;
    @FXML
    private TableColumn<Hotel, Integer> col_id;
    @FXML
    private TableColumn<Hotel, String> col_type;
    @FXML
    private TableColumn<Hotel, Integer> col_kprice;
    @FXML
    private TableColumn<Hotel, Integer> col_rate;
    @FXML
    private TableColumn<Hotel, Integer> col_std;
    @FXML
    private TableColumn<Hotel, String> col_amen;
    @FXML
    private TableColumn<Hotel, Integer> col_max;
    @FXML
    private TableColumn<Hotel, String> col_name;
    @FXML
    private TableView<Hotel> table_hotel;


    //Method called when screen is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillHotels();
    }

    //Create Hotel Function
    @FXML
    void addHotelButtonClicked(ActionEvent event) {

    }


    //Create Admin Account
    @FXML
    public void createAdminButtonClicked(Event e) throws IOException {
        System.out.println("Create Admin Button");

        //checks for empty fields
        if (createUserAdmin.getText().isEmpty()) { //errors here if one text box is empty
            System.out.println("Please enter Username");
            errorAdminOutput.setText("Please enter a Username");
        }
        else if (createPassAdmin.getText().isEmpty()) {
            System.out.println("Please enter Password");
            errorAdminOutput.setText("Please enter a Password");
        }
        else if (createFirstNAdmin.getText().isEmpty()) {
            System.out.println("Please enter First Name");
            errorAdminOutput.setText("Please enter a First Name");
        }
        else if (createLastNAdmin.getText().isEmpty()) {
            System.out.println("Please enter Last Name");
            errorAdminOutput.setText("Please enter a Last Name");
        }
        else if (createEmailAdmin.getText().isEmpty()) {
            System.out.println("Please enter Email");
            errorAdminOutput.setText("Please enter an Email");
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

                labelAdminOuput.setText("Success!"); //maybe?

                //Clear ALL text fields
                createUserAdmin.clear();
                createPassAdmin.clear();
                createFirstNAdmin.clear();
                createLastNAdmin.clear();
                createEmailAdmin.clear();
                errorAdminOutput.setText("");
                //****************DISPLAYS TO THE USER SUCCESS THEN CLEARS DISPLAY TEXT********//
                PauseTransition pause = new PauseTransition();
                pause.setDuration(Duration.seconds(2));
                pause.setOnFinished(event->labelAdminOuput.setText(" "));
                pause.play();

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

    //Fill addRoom Table
    public void fillHotels()
    {
        //Populate cells to respective values
        col_id.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("hotelID"));
        col_name.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        col_type.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelType"));
        col_amen.setCellValueFactory(new PropertyValueFactory<Hotel, String>("amenities"));
        col_max.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("maxRooms"));
        col_std.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("standardPrice"));
        col_qprice.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("queenPrice"));
        col_kprice.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("kingPrice"));
        col_rate.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("weekendRate"));

        //Launch DB instance
        hotelList = FXCollections.observableArrayList();
        Connection connectDB = DBConnection.getConnection();
        String selectQuery = "SELECT * FROM hotel_db.Hotel";

        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                hotelList.add(new Hotel(rs.getInt("hotelID"), rs.getString("hotelName"), rs.getString("hotelType"), rs.getString("amenities"), rs.getInt("maxRooms"), rs.getInt("standardPrice"), rs.getInt("queenPrice"), rs.getInt("kingPrice"), rs.getInt("weekendRate")));
            }

            System.out.println(hotelList);
            table_hotel.setItems(hotelList);

        } catch (Exception e){
            System.out.println(e);
        }


    }



}