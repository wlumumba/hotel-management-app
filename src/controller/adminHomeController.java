package controller;

import helpers.*;
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
    //Instance Variables
    User currentUser;
    Hotel currentHotel;
    ObservableList<Hotel> hotelList;

    //FXML Variables
    @FXML
    private TextField hotelIDField;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField hotelTypeField;
    @FXML
    private TextField amenitiesField;
    @FXML
    private TextField maxRoomsField;
    @FXML
    private TextField standardPriceField;
    @FXML
    private TextField queenPriceField;
    @FXML
    private TextField kingPriceField;
    @FXML
    private TextField weekendRateField;
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
    private TextField editHotelAmenities;
    @FXML
    private TextField editHotelType;
    @FXML
    private TextField editHotelWknPrice;
    @FXML
    private TextField editHotelKingPrice;
    @FXML
    private TextField editHotelStdPrice;
    @FXML
    private TextField editHotelQuePrice;
    @FXML
    private TextField hotelEditSearch;


    //Buttons
    @FXML
    private Button addHotelButton;
    @FXML
    private Label errorAdminOutput;
    //// ADD ROOM ELEMENTS ///
    private Button createAdminButton;
    @FXML
    private Button editHotelButton;
    @FXML
    private Button submitHotelEditButton;


    //columns
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
    @FXML
    private Button addRoomButton;
    @FXML
    private TextField addroom_hotelid;
    @FXML
    private TextField addroom_bed;
    @FXML
    private TextField addroom_price;

    //labels
    @FXML
    private Label labelAdminOuput;

    //Method called when screen is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillHotels();
        //Functions.createReservation(new Reservation(1, 6, 1, "01/1/2000", "02/2/2000", "test", true));
    }

    //Create Hotel Function
    @FXML
    public void addHotelButtonClicked(ActionEvent e) throws IOException{

        System.out.println("Create hotel Button");

        if (hotelNameField.getText().isEmpty()) {
            System.out.println("Please enter hotel Name");
            errorAdminOutput.setText("Please enter a hotel Name");
        }
        else if (hotelTypeField.getText().isEmpty()) {
            System.out.println("Please enter hotel type");
            errorAdminOutput.setText("Please enter a hotel type");
        }
        else if (amenitiesField.getText().isEmpty()) {
            System.out.println("Please enter amenities");
            errorAdminOutput.setText("Please enter a amenities");
        }
        else if (maxRoomsField.getText().isEmpty()) {
            System.out.println("Please enter max rooms");
            errorAdminOutput.setText("Please enter max rooms");
        }
        else if (standardPriceField.getText().isEmpty()) {
            System.out.println("Please enter standard room price");
            errorAdminOutput.setText("Please enter standard room price");
        }
        else if (queenPriceField.getText().isEmpty()) {
            System.out.println("Please enter queen room price");
            errorAdminOutput.setText("Please enter queen room price");
        }
        else if (kingPriceField.getText().isEmpty()) {
            System.out.println("Please enter king room price");
            errorAdminOutput.setText("Please enter king room price");
        }
        else if (weekendRateField.getText().isEmpty()) {
            System.out.println("Please enter weekend rate");
            errorAdminOutput.setText("Please enter weekend rate");
        }
        else {

            //Launching DataBase Instance
            currentHotel = new Hotel(0, hotelNameField.getText(), hotelTypeField.getText(), amenitiesField.getText(), Integer.parseInt(maxRoomsField.getText()), Integer.parseInt(standardPriceField.getText()), Integer.parseInt(queenPriceField.getText()), Integer.parseInt(kingPriceField.getText()), Integer.parseInt(weekendRateField.getText()));

            Hotel.addHotel(currentHotel);

            hotelNameField.clear();
            hotelTypeField.clear();
            amenitiesField.clear();
            maxRoomsField.clear();
            standardPriceField.clear();
            queenPriceField.clear();
            kingPriceField.clear();
            weekendRateField.clear();

        }

    }

    //Create Room Function
    @FXML
    void addRoomButtonClicked(ActionEvent event){
        System.out.println("Create Room Button");
        Connection connectDB = DBConnection.getConnection();

        String insertQuery = "INSERT INTO hotel_db.Room (bedType, Hotel_hotelID) VALUES (?, ?)";

        // INSERT INTO `hotel_db`.`Room` (`roomID`, `bedType`, `price`, `Hotel_hotelID`) VALUES ('', 'Queen', '124', '1');
        // Run a query to get hotel entry with given hotelID, strip the price (error check room type)
        try{
            PreparedStatement ps = connectDB.prepareStatement(insertQuery);
            ps.setString(1, addroom_bed.getText());
            ps.setInt(2, Integer.parseInt(addroom_hotelid.getText()));
            
            ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }




    //Create Admin Account Function
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
            User.createAdmin(currentUser);

            //Clear ALL text fields
            createUserAdmin.clear();
            createPassAdmin.clear();
            createFirstNAdmin.clear();
            createLastNAdmin.clear();
            createEmailAdmin.clear();
            errorAdminOutput.setText("");

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

    @FXML
    void editHotelButtonClicked(ActionEvent event) {
        String input = hotelEditSearch.getText();

        Hotel editHotel = Hotel.getHotelFromName(input);
        System.out.println(editHotel.toString());

        editHotelAmenities.setText(editHotel.getAmenities());
        editHotelType.setText(editHotel.getHotelType());
        editHotelStdPrice.setText(String.valueOf(editHotel.getStandardPrice()));
        editHotelQuePrice.setText(String.valueOf(editHotel.getQueenPrice()));
        editHotelKingPrice.setText(String.valueOf(editHotel.getKingPrice()));
        editHotelWknPrice.setText(String.valueOf(editHotel.getWeekendRate()));

    }

    @FXML
    void submitHotelEditButton(ActionEvent event) {

        Hotel editHotel = new Hotel(0, hotelEditSearch.getText(), editHotelType.getText(), editHotelAmenities.getText(), 0, Integer.parseInt(editHotelStdPrice.getText()), Integer.parseInt(editHotelQuePrice.getText()), Integer.parseInt(editHotelKingPrice.getText()), Integer.parseInt(editHotelWknPrice.getText()));
        Hotel.updateHotel(editHotel);

        editHotelAmenities.clear();
        editHotelType.clear();
        editHotelStdPrice.clear();
        editHotelQuePrice.clear();
        editHotelKingPrice.clear();
        editHotelWknPrice.clear();
        hotelEditSearch.clear();

    }



}