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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class adminHomeController implements Initializable {
    //Instance Variables
    public User currentUser;
    public Hotel currentHotel;
    public ObservableList<Hotel> hotelList;

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

    @FXML
    private TextField editUsername;

    @FXML
    private TextField editUserFName;

    @FXML
    private TextField editUserLName;

    @FXML
    private TextField editUserPW;

    @FXML
    private TextField editUserAccountType;

    @FXML
    private TextField userEditSearch;



    //Buttons
    @FXML
    private Button addHotelButton;
    @FXML
    private Label errorAdminOutput;
    @FXML
    private Button editUserSearchButton;
    @FXML
    private Button editUserSubmitButton;
    //// ADD ROOM ELEMENTS ///
    @FXML
    private Button createAdminButton;
    @FXML
    private Button editHotelButton;
    @FXML
    private Button submitHotelEditButton;


    //columns

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

    private boolean counter = true;

    /********************** Initialize method called when screen is loaded ************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAddRoomTable();
        fillCreateReserveTable();
        //Functions.createReservation(new Reservation(1, 6, 1, "01/1/2000", "02/2/2000", "test", true));
    }

    /******************************   Create Hotel Function ***************************************/
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

    /************************** EDIT Hotel TAB    **************************************************/
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

    /*********************************  Create Room TAB  ******************************************/
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

    // Fills the table given a List
    public void fillAddRoomTable()
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

        //Method call to populate the table
        table_hotel.setItems(Functions.populateHotelTable());
    }

    @FXML
    void addRoomButtonClicked(ActionEvent event){
        System.out.println("Create Room Button");
        Connection connectDB = DBConnection.getConnection();

        String hotelQuery = "SELECT * FROM hotel_db.Hotel where hotelID = ";
        String insertQuery = "INSERT INTO hotel_db.Room (bedType, roomPrice, Hotel_hotelID) VALUES (?, ?, ?)";
        int roomPrice = 0;

        // INSERT INTO `hotel_db`.`Room` (`roomID`, `bedType`, `price`, `Hotel_hotelID`) VALUES ('', 'Queen', '124', '1');
        try{
            PreparedStatement ps = connectDB.prepareStatement(insertQuery);

            //Also grab the proper hotel entry to assign each room a price
            ResultSet rs = ps.executeQuery(hotelQuery + Integer.parseInt(addroom_hotelid.getText()));
            rs.next();

            if (addroom_bed.getText().equalsIgnoreCase("standard"))
                roomPrice = rs.getInt(6);
            else if (addroom_bed.getText().equalsIgnoreCase("queen"))
                roomPrice = rs.getInt(7);
            else if (addroom_bed.getText().equalsIgnoreCase("king"))
                roomPrice = rs.getInt(8);

            System.out.println(roomPrice);

            ps.setString(1, addroom_bed.getText());
            ps.setInt(2, roomPrice);
            ps.setInt(3, Integer.parseInt(addroom_hotelid.getText()));

            
            ps.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }




    //*****************************  Create Admin Account TAB *******************************************/
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

    /********************************************CREATE RESERVATION TAB*****************************************/
    @FXML
    private TableColumn<Hotel, String> col_HotelTypeR;
    @FXML
    private TableColumn<Hotel, String> col_HotelR;
    @FXML
    private TableColumn<Hotel, String> col_AmenitiesR;
    @FXML
    private TableView<Hotel> table_hotelR;
    /**
     * Fills out the CreateReservationTable
     */
    private void fillCreateReserveTable () {
        col_HotelR.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        col_HotelTypeR.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelType"));
        col_AmenitiesR.setCellValueFactory(new PropertyValueFactory<Hotel, String>("amenities"));

        hotelList = Functions.populateHotelTable();
        table_hotelR.setItems(hotelList);
    }


    @FXML
    private TextField hotelNameR;
    @FXML
    private TextField startDate;
    @FXML
    private TextField endDate;
    @FXML
    private Label warningLabel;

    /**
     * If the user submits all the correct data, then it will go to searchReservation.fxml
     * and let the user select the room etc.
     */
    @FXML
    private void searchButtonClicked (ActionEvent event) throws IOException { //Event or ActionEvent?
        System.out.println("Reservation Search Button Clicked");
        int hotelID = -1;
        //warningLabel.setText(""); // USED TO RESET THE LABEL


        System.out.print("This is hotel Name: " + hotelNameR.getText() + "\n");

        try {
            //Error check user entered HotelName: Needs work!
           for (int i = 0; i < hotelList.size(); i++) {
            //   System.out.println("HOTEL LIST: " + hotelList.get(i).getHotelName() + "\n");

               if ((hotelNameR.getText().isEmpty()) || (hotelList.get(i).getHotelName().equals(hotelNameR.getText()))) { //it still verify correct hotel yet
                   System.out.println("Please enter a valid hotel\n");
               }
           }

           //Error checked user startDate and endDate
            final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
            if (!startDate.getText().isEmpty() || !endDate.getText().isEmpty()) {
                sdf.parse(startDate.getText());
                sdf.parse(endDate.getText());
            }
            else{
                //If user specifies no date range
                startDate.setText("0000/00/00");
                endDate.setText("9999/00/00");
            }

            //compare to the SQL DATABASE FOR CORRECT HOTEL AND PRICE RANGE
            System.out.println("Searching User Input");

            //Get hotelID of user entered hotelName
            for(Hotel h : hotelList){
                if(h.getHotelName().equalsIgnoreCase(hotelNameR.getText()))
                    hotelID = h.getHotelID();
            }

            //Pass hotelID, startDate, endDate to fill table of next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/styles/singleReservationRoom.fxml"));
            Parent root = loader.load();
            singleReservationRoomController scene2 = loader.getController();
            scene2.fillChooseRoomTable(new String[]{String.valueOf(hotelID), startDate.getText(), endDate.getText()});

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in searchButtonClicked ");
        }
        catch(ParseException e){
            System.out.println("Date in wrong format! Should be YYYY/MM/DD");
        }

    }


    /****************************************** LOGOUT TAB ******************************************/
    @FXML
    private Button adminLogoutButton;

    @FXML
    void adminLogoutButtonClicked(ActionEvent event) throws IOException {

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



    /**
     * Helper function, Checks if the maxPrice.getText and minPrice.getText is a numeric value
     * could be modified a bit
     */
    public static boolean isNumeric (String input) { //maybe move inside searchButtonClicked function
        try {
            int val = Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            System.out.println(("Error in isNumeric"));
        }
        return false;
    }

    /*******------------reservationRoom FXML-------------*****/
   /* //  NEEDS ITS OWN CONTROLLER!!! ///
    @FXML
    private TextField roomR;
    @FXML
    private TextField emailR;
    @FXML
    private TextField startDate; //not sure about these yet
    @FXML
    private TextField endDate; //not sure about these yet


    *//**
     * Maybe here check for account type or should it ask before? I think it should check
     * This Function will let the user select the room and then reserve the room.
     * Must provide a valid email and room
     *//*
    @FXML
    private void reserveButton(ActionEvent event) throws IOException  {
        System.out.println("Reserve Room Button");
      //  try {
            if (emailR.getText().isEmpty()) {  //|| (hotelList.equals(hotelNameR.getText()))) { //may be incorrect
                System.out.println("Please enter a valid email\n");
                warningLabel.setText("Enter a valid email");
            } else if (roomR.getText().isEmpty() || (!isNumeric(maxPriceR.getText()))) {
                System.out.println("Please enter a valid Room\n");
                warningLabel.setText("Enter a valid Room");
            }
            //MAYBE ASK USER TO SELECT FROM CALENDAR OR THE USER MUST PUT START DATE IN MONTH/DAY
            //THEN END DATE MONTH/DAY AND THEN HAVE A DATABASE/RESERVATION FOR THE USER FOR THAT ROOM?
            //THIS WILL HAVE TO CHECK IF ROOM IS AVAILABLE IF TRUE ELSE DON'T DISPLAY AND WAIT UNTIL ROOM IS AVAILABLE?
            //DOES THIS HAVE TO BE IN REAL TIME?

     //   }
       *//* catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in ReserveButton");
        }*//*

    }

    @FXML
    private void returnButton(ActionEvent event) throws IOException {
        System.out.println("Returning to Home Admin");
        try {
            Stage stage;
            Scene scene;
            Parent root;

            root = FXMLLoader.load((getClass().getResource("/styles/adminHome.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene((root));

            stage.setScene((scene));
            stage.show();
        }
         catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error in searchButtonClicked ");
                }
    }*/

    /******************************************************************************************************/

    @FXML
    void submitUserEditButtonClicked(ActionEvent event) {
        User updateUser = new User(editUsername.getText(), editUserFName.getText(), editUserLName.getText(), userEditSearch.getText(), editUserPW.getText(), Integer.parseInt(editUserAccountType.getText()));

        User.updateUser(updateUser);

        userEditSearch.clear();
        editUsername.clear();
        editUserFName.clear();
        editUserLName.clear();
        editUserPW.clear();
        editUserAccountType.clear();


    }

    @FXML
    void editUserSearchButtonClicked(ActionEvent event) {

        String input = userEditSearch.getText();

        User editUser = User.getUserFromEmail(input);
        System.out.println(editUser.toString());

        editUsername.setText(editUser.getUserName());
        editUserFName.setText(editUser.getFirstName());
        editUserLName.setText(editUser.getLastName());
        editUserPW.setText(editUser.getPassword());
        editUserAccountType.setText(String.valueOf(editUser.getAccountType()));

    }


} //PUBLIC CLASS END