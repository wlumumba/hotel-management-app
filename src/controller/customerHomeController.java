package controller;

import helpers.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.ResourceBundle;

public class customerHomeController implements Initializable {

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
    public User currentUser;
    public Hotel currentHotel;
    public ObservableList<Hotel> hotelList;
    public ObservableList<Reservation> reservationList;
    public ObservableList<Room> roomList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fillAddRoomTable();
        fillCreateReserveTable();
        //Functions.createReservation(new Reservation(1, 6, 1, "01/1/2000", "02/2/2000", "test", true));
    }


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
            scene2.fillChooseRoomTable(new String[] {String.valueOf(hotelID), startDate.getText(), endDate.getText(), "0"});

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

    @FXML
    private TextField deleteResId;

    @FXML
    void deleteERButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Edit Reservation deleteERButtonClicked");

        Connection connectDB = DBConnection.getConnection();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();

        //warningLabel.setText(""); // USED TO RESET THE LABEL

        String query = "DELETE FROM hotel_db.Reservation WHERE reservationId = " + deleteResId.getText() + " AND User_email = \"" + u.getEmail() +"\"";
        System.out.println(query);

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            int deleted = ps.executeUpdate();

            if(deleted == 0)
                System.out.println("You have no reservation with this ID");

        } catch (Exception e){
            System.out.println(e);
        }

    }


    /***************************EDIT RESERVATION TAB************************/
    @FXML
    public TextField currentResID;
    @FXML
    private TextField newStartDate;
    @FXML
    private TextField newEndDate;


    @FXML
    void submitERButtonClicked(ActionEvent event) throws IOException {
        System.out.println("Edit Reservation submitERButtonClicked");
        int roomID = -1;
        int hotelID = -1;

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();

        //System.out.println("INSIDE submitERButtonClicked");

        try {
            //Error check to see if Reservation ID exists//Should there be a safeguard? like if RESERVATION ID is equal to EMAIL under admin?
            //could it be I populated reservationList wrong or roomList wrong?
            reservationList = Functions.getNewReservationList();

            // System.out.println("INSIDE TRY ");
            //  System.out.println("RES OUTSIDE: " + reservationList.size());

            if (currentResID.getText().isEmpty()) { //|| re.getReservationID() != Integer.parseInt(currentResID.getText())) { //needs to check with list from reservation
                System.out.println("Please enter a valid reservation ID\n");
            }
            //   System.out.println("OUTSIDE SECOND FOR LOOP: ");
            //Error checked user startDate and endDate
            final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy/MM/dd");
            if (!newStartDate.getText().isEmpty() || !newEndDate.getText().isEmpty()) {
                sdf.parse(newStartDate.getText());
                sdf.parse(newEndDate.getText());
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
                if((re.getReservationID() == Integer.parseInt(currentResID.getText())) && (re.getUserEmail().equals(u.getEmail()))){
                    roomID = re.getRoomID();
                    for (Room r : roomList) {
                        if (r.getRoomID() == roomID)
                        {
                            hotelID = r.getHotelID();
                        }
                    }
                }
            }

            /**************MAKE NEW RESERVATION HERE**************/
            if (hotelID > -1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/styles/singleReservationRoom.fxml"));
                Parent root = loader.load();
                singleReservationRoomController scene2 = loader.getController();
                scene2.fillChooseRoomTable(new String[]{String.valueOf(hotelID), newStartDate.getText(), newEndDate.getText(), currentResID.getText()});

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene newScene = new Scene(root);
                currentStage.setScene(newScene);
                currentStage.show();
            }
            System.out.println("No Reservation Found, Try again");

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in submitButtonClicked ");
        }
        catch(ParseException e){
            System.out.println("Date in wrong format! Should be YYYY/MM/DD");
        }

    }


}

