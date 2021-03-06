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
import java.util.Date;
import java.util.ResourceBundle;

public class adminHomeController implements Initializable {
    //Instance Variables
    public User currentUser;
    public Hotel currentHotel;
    public ObservableList<Hotel> hotelList;
    public ObservableList<Reservation> reservationList;
    public ObservableList<Room> roomList;




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




    @FXML
    private Label errorAdminOutput;
    @FXML
    private Button editUserSearchButton;
    @FXML
    private Button editUserSubmitButton;
    //// ADD ROOM ELEMENTS ///
    @FXML
    private Button createAdminButton;



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

    /********************** Initialize method called when screen is loaded ************************/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillAddRoomTable();
        fillCreateReserveTable();
        //Functions.createReservation(new Reservation(1, 6, 1, "01/1/2000", "02/2/2000", "test", true));
    }

    /******************************   Create Hotel Function ***************************************/
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
    private Label addHotelOutput;
    @FXML
    private Button addHotelButton;



    @FXML
    public void addHotelButtonClicked(ActionEvent e) throws IOException{

        System.out.println("Create hotel Button");

        if (hotelNameField.getText().isEmpty()) {
            System.out.println("Please enter hotel Name");
            addHotelOutput.setText("Please enter a hotel Name");
        }
        else if (hotelTypeField.getText().isEmpty()) {
            System.out.println("Please enter hotel type");
            addHotelOutput.setText("Please enter a hotel type");
        }
        else if (amenitiesField.getText().isEmpty()) {
            System.out.println("Please enter amenities");
            addHotelOutput.setText("Please enter amenities");
        }
        else if (maxRoomsField.getText().isEmpty()) {
            System.out.println("Please enter max rooms");
            addHotelOutput.setText("Please enter max rooms");
        }
        else if (standardPriceField.getText().isEmpty()) {
            System.out.println("Please enter standard room price");
            addHotelOutput.setText("Please enter standard room price");
        }
        else if (queenPriceField.getText().isEmpty()) {
            System.out.println("Please enter queen room price");
            addHotelOutput.setText("Please enter queen room price");
        }
        else if (kingPriceField.getText().isEmpty()) {
            System.out.println("Please enter king room price");
            addHotelOutput.setText("Please enter king room price");
        }
        else if (weekendRateField.getText().isEmpty()) {
            System.out.println("Please enter weekend rate");
            addHotelOutput.setText("Please enter weekend rate");
        }
        else {

            //Launching DataBase Instance
            currentHotel = new Hotel(0, hotelNameField.getText(), hotelTypeField.getText(), amenitiesField.getText(), Integer.parseInt(maxRoomsField.getText()), Integer.parseInt(standardPriceField.getText()), Integer.parseInt(queenPriceField.getText()), Integer.parseInt(kingPriceField.getText()), Integer.parseInt(weekendRateField.getText()));

            Hotel.addHotel(currentHotel);

            addHotelOutput.setText("Hotel '" + currentHotel.getHotelName() + "' added to system");

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
    private Button editHotelButton;
    @FXML
    private Button submitHotelEditButton;
    @FXML
    private Label editHotelOutput;
    @FXML
    private Label editHotelOutput1;


    @FXML
    void editHotelButtonClicked(ActionEvent event) {
        String input = hotelEditSearch.getText();
        editHotelOutput.setText(" ");

        Hotel editHotel = Hotel.getHotelFromName(input);
        //System.out.println(editHotel.toString());

        if(editHotel.getHotelName().equals("hotel name"))
            editHotelOutput.setText("Hotel '" + hotelEditSearch.getText() +"' not found, please try again");

        else {

            editHotelOutput.setText("Edit hotel '" + hotelEditSearch.getText() +"'");

            editHotelAmenities.setText(editHotel.getAmenities());
            editHotelType.setText(editHotel.getHotelType());
            editHotelStdPrice.setText(String.valueOf(editHotel.getStandardPrice()));
            editHotelQuePrice.setText(String.valueOf(editHotel.getQueenPrice()));
            editHotelKingPrice.setText(String.valueOf(editHotel.getKingPrice()));
            editHotelWknPrice.setText(String.valueOf(editHotel.getWeekendRate()));
        }
    }

    @FXML
    void submitHotelEditButton(ActionEvent event) {
        editHotelOutput1.setText(" ");

        Hotel editHotel = new Hotel(0, hotelEditSearch.getText(), editHotelType.getText(), editHotelAmenities.getText(), 0, Integer.parseInt(editHotelStdPrice.getText()), Integer.parseInt(editHotelQuePrice.getText()), Integer.parseInt(editHotelKingPrice.getText()), Integer.parseInt(editHotelWknPrice.getText()));
        int errorCode = Hotel.updateHotel(editHotel);

        if(errorCode == 0)
            editHotelOutput1.setText("An error has occurred, please try again. If error persist please contact customer service");

        else {
            editHotelOutput1.setText("Successfully updated hotel '" + hotelEditSearch.getText() + "'");
            editHotelOutput.setText(" ");
            editHotelAmenities.clear();
            editHotelType.clear();
            editHotelStdPrice.clear();
            editHotelQuePrice.clear();
            editHotelKingPrice.clear();
            editHotelWknPrice.clear();
            hotelEditSearch.clear();
        }
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
    @FXML
    private Label addRoomOutput;

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
        addRoomOutput.setText(" ");
        Connection connectDB = DBConnection.getConnection();

        if(addroom_hotelid.getText().isEmpty())
            addRoomOutput.setText("Please enter Hotel ID");

        else if (addroom_bed.getText().isEmpty())
            addRoomOutput.setText("Please enter bed type");

        else {

            String hotelQuery = "SELECT * FROM hotel_db.Hotel where hotelID = ";
            String insertQuery = "INSERT INTO hotel_db.Room (bedType, roomPrice, Hotel_hotelID) VALUES (?, ?, ?)";
            int roomPrice = 0;

            // INSERT INTO `hotel_db`.`Room` (`roomID`, `bedType`, `price`, `Hotel_hotelID`) VALUES ('', 'Queen', '124', '1');
            try {
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
                addRoomOutput.setText("Successfully added room with type '" + addroom_bed.getText() + "' to hotel '" + addroom_hotelid.getText() + "'");
            } catch (Exception ex) {
                addRoomOutput.setText("Could not add room with type '" + addroom_bed.getText() + "' to hotel '" + addroom_hotelid.getText() + "'. Please Try again, if error persist contact customer service");
                System.out.println(ex);
            }
        }
    }




    //*****************************  Create Admin Account TAB *******************************************/

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
    private Label addAdminOutput;

    @FXML
    public void createAdminButtonClicked(Event e) throws IOException {
        System.out.println("Create Admin Button");
        addAdminOutput.setText(" ");

        //checks for empty fields
        if (createUserAdmin.getText().isEmpty()) { //errors here if one text box is empty
            System.out.println("Please enter Username");
            addAdminOutput.setText("Please enter a Username");
        }
        else if (createPassAdmin.getText().isEmpty()) {
            System.out.println("Please enter Password");
            addAdminOutput.setText("Please enter a Password");
        }
        else if (createFirstNAdmin.getText().isEmpty()) {
            System.out.println("Please enter First Name");
            addAdminOutput.setText("Please enter a First Name");
        }
        else if (createLastNAdmin.getText().isEmpty()) {
            System.out.println("Please enter Last Name");
            addAdminOutput.setText("Please enter a Last Name");
        }
        else if (createEmailAdmin.getText().isEmpty()) {
            System.out.println("Please enter Email");
            addAdminOutput.setText("Please enter an Email");
        }
        else {
            currentUser = new User(createUserAdmin.getText(), createFirstNAdmin.getText(), createLastNAdmin.getText(), createEmailAdmin.getText(), createPassAdmin.getText(), 1);

            //Launching DataBase Instance
            int code = User.createAdmin(currentUser);

            if (code == 0)
                addAdminOutput.setText("Unknown Error");

            else if(code == 2)
                addAdminOutput.setText("Username or email taken. Try another username and/or email");

            else {
                //Clear ALL text fields
                addAdminOutput.setText("Account with username '" + createUserAdmin.getText() +"' created");
                createUserAdmin.clear();
                createPassAdmin.clear();
                createFirstNAdmin.clear();
                createLastNAdmin.clear();
                createEmailAdmin.clear();
            }
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
    @FXML
    private Label addResOutput;
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
    private void searchButtonClicked (ActionEvent event) throws Exception { //Event or ActionEvent?
        System.out.println("Reservation Search Button Clicked");
        addResOutput.setText(" ");
        boolean hotelNameValid = false;

        if(hotelNameR.getText().isEmpty())
            addResOutput.setText("Please enter hotel name");

        else if(startDate.getText().isEmpty())
            addResOutput.setText("Please enter valid start date");

        else if(endDate.getText().isEmpty())
            addResOutput.setText("Please enter valid end date");

        else {

            int hotelID = -1;
            //warningLabel.setText(""); // USED TO RESET THE LABEL

            System.out.print("This is hotel Name: " + hotelNameR.getText() + "\n");

            try {
                //Error check user entered HotelName: Needs work!
                for (int i = 0; i < hotelList.size(); i++) {

                    if ((hotelList.get(i).getHotelName().equals(hotelNameR.getText()))) { //it still verify correct hotel yet
                        //addResOutput.setText("Please enter valid hotel name");
                        System.out.println("Hotel found");
                        hotelNameValid = true;
                    }
                }

                if (!hotelNameValid) {
                    addResOutput.setText("Hotel name not found, please try again");
                    throw new Exception();
                }

                //Error checked user startDate and endDate
                final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
                if (!startDate.getText().isEmpty() || !endDate.getText().isEmpty()) {
                    Date dateS = sdf.parse(startDate.getText());
                    Date dateE = sdf.parse(endDate.getText());

                    if(dateS.after(dateE) || dateS.equals(dateE)) {
                        throw new ParseException("", -1);
                    }

                } else {
                    //If user specifies no date range
                    addResOutput.setText("No date range specified");
                    startDate.setText("0000/00/00");
                    endDate.setText("9999/00/00");
                }

                //compare to the SQL DATABASE FOR CORRECT HOTEL AND PRICE RANGE
                System.out.println("Searching User Input");

                //Get hotelID of user entered hotelName
                for (Hotel h : hotelList) {
                    if (h.getHotelName().equalsIgnoreCase(hotelNameR.getText()))
                        hotelID = h.getHotelID();
                }

                //Pass hotelID, startDate, endDate to fill table of next scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/styles/singleReservationRoom.fxml"));
                Parent root = loader.load();
                singleReservationRoomController scene2 = loader.getController();
                scene2.fillChooseRoomTable(new String[]{String.valueOf(hotelID), startDate.getText(), endDate.getText(), "0"});

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene newScene = new Scene(root);
                currentStage.setScene(newScene);
                currentStage.show();

            } catch(IOException e){
                e.printStackTrace();
                addResOutput.setText("Unknown error!");
                System.out.println("Error in searchButtonClicked ");
            } catch(ParseException e){
                addResOutput.setText("Date in wrong format!");
                System.out.println("Date in wrong format!");
            }

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
    }

    /******************************************************************************************************/
    @FXML
    private Label editUserOutput;

    @FXML
    void submitUserEditButtonClicked(ActionEvent event) {
        User updateUser = new User(editUsername.getText(), editUserFName.getText(), editUserLName.getText(), userEditSearch.getText(), editUserPW.getText(), Integer.parseInt(editUserAccountType.getText()));
        editUserOutput.setText(" ");
        int code = User.updateUser(updateUser);

        if(code == 0)
            editUserOutput.setText("Unknown error");

        else {
            editUserOutput.setText("Successfully updated user '" + userEditSearch.getText() +"'");
            userEditSearch.clear();
            editUsername.clear();
            editUserFName.clear();
            editUserLName.clear();
            editUserPW.clear();
            editUserAccountType.clear();
        }

    }

    @FXML
    void editUserSearchButtonClicked(ActionEvent event) {

        String input = userEditSearch.getText();
        editUserOutput.setText(" ");

        User editUser = User.getUserFromEmail(input);

        if(editUser.getUserName().equals("user name")) {
            editUserOutput.setText("Email not found, please try again");

        }
        else {
            editUsername.setText(editUser.getUserName());
            editUserFName.setText(editUser.getFirstName());
            editUserLName.setText(editUser.getLastName());
            editUserPW.setText(editUser.getPassword());
            editUserAccountType.setText(String.valueOf(editUser.getAccountType()));
        }

    }

    /********************************************  EDIT RESERVATION TAB ******************************************/
    @FXML
    public TextField currentResID;
    @FXML
    private TextField newStartDate;
    @FXML
    private TextField newEndDate;
    @FXML
    private Label editResOutput;
    @FXML
    private Label editResOutput1;
    @FXML
    void submitERButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Edit Reservation submitERButtonClicked");
        int roomID = -1;
        int hotelID = -1;
        editResOutput.setText(" ");
        boolean resIDFound = false;


        try {
            //could it be I populated reservationList wrong or roomList wrong?
            reservationList = Functions.getNewReservationList();

            if (currentResID.getText().isEmpty()) { //|| re.getReservationID() != Integer.parseInt(currentResID.getText())) { //needs to check with list from reservation
                editResOutput.setText("Please enter reservation ID");
                throw new Exception("no id entered");
            }
           else if (newStartDate.getText().isEmpty()) { //|| re.getReservationID() != Integer.parseInt(currentResID.getText())) { //needs to check with list from reservation
                editResOutput.setText("Please enter new start date");
                throw new Exception("new start date empty");
            }
            else if (newEndDate.getText().isEmpty()) { //|| re.getReservationID() != Integer.parseInt(currentResID.getText())) { //needs to check with list from reservation
                editResOutput.setText("Please enter new end date");
                throw new Exception("new end date empty");
            }


            //Error checked user startDate and endDate
            final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
            if (!newStartDate.getText().isEmpty() || !newEndDate.getText().isEmpty()) {
                Date dateS = sdf.parse(newStartDate.getText());
                Date dateE = sdf.parse(newEndDate.getText());

            }
            else{
                //If user specifies no date range
                newStartDate.setText("0000/00/00");
                newEndDate.setText("9999/00/00");
            }

            //compare to the SQL DATABASE FOR CORRECT HOTEL AND PRICE RANGE
            System.out.println("Searching User Input");
            //Goes backward and getsthe hotelId from ReservationID->RoomID->HotelID
            roomList = Functions.getNewRoomList();
            for(Reservation re : reservationList){
                if(re.getReservationID() == Integer.parseInt(currentResID.getText())){
                    resIDFound = true;
                    roomID = re.getRoomID();
                    for (Room r : roomList) {
                        if (r.getRoomID() == roomID)
                        {
                            hotelID = r.getHotelID();
                        }
                    }
                }
            }

            if(!resIDFound){
                editResOutput.setText("Reservation not found.\nCheck Reservation ID and try again");
                throw new Exception("Res ID not found");
            }

            /**************MAKE NEW RESERVATION HERE**************/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/styles/singleReservationRoom.fxml"));
            Parent root = loader.load();
            singleReservationRoomController scene2 = loader.getController();
            scene2.fillChooseRoomTable(new String[]{String.valueOf(hotelID), newStartDate.getText(), newEndDate.getText(), currentResID.getText()});

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in submitButtonClicked ");
            editResOutput.setText("Unknown error");
        }
        catch(ParseException e){
            System.out.println("Date in wrong format!");
            editResOutput.setText("Date in wrong format!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***********************************************************************************************************/
    @FXML
    private TextField deleteResId;

    @FXML
    void deleteERButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Edit Reservation deleteERButtonClicked");
        editResOutput1.setText(" ");
        int deleteCount;

        if (deleteResId.getText().isEmpty()) {
            editResOutput1.setText("Please enter Reservation ID");
        } else {

        Connection connectDB = DBConnection.getConnection();



            //warningLabel.setText(""); // USED TO RESET THE LABEL

            String query = "DELETE FROM hotel_db.Reservation WHERE reservationId = " + deleteResId.getText(); // THIS IS ONLY FOR CUSTOMER SIDE + "email = " + deletEmail.getTxt();

            try {
                if (deleteResId.getText().isEmpty()) {
                    editResOutput1.setText("Please enter Reservation ID");
                    throw new Exception("Res ID not found");
                }

                PreparedStatement ps = connectDB.prepareStatement(query);
                deleteCount = ps.executeUpdate();

                if(deleteCount == 0){
                    editResOutput1.setText("Reservation ID not found.\nCheck ID and try again");
                }

                else {
                    editResOutput1.setText("Reservation with ID '" + deleteResId.getText() + "' successfully deleted");
                    deleteResId.clear();
                }
            } catch (Exception e) {
                editResOutput1.setText("Unknown error. Please try again");
                System.out.println(e);
            }
        }
    }
} //PUBLIC CLASS END