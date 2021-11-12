
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class singleReservationRoomController implements Initializable {

    //Method called when screen is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fillChooseRoomTable(); //gets called from adminHomeController!
    }



    /************************************* CHOOSE A ROOM TAB *****************************************/

    @FXML
    private TextField roomR;
    @FXML
    private TextField emailR;
    @FXML
    private Label successLabel;

    @FXML
    private TableColumn<Room, Integer> col_roomidR;
    @FXML
    private TableColumn<Room, String> col_bedtypeR;
    @FXML
    private TableColumn<Room, Integer> col_roompriceR;
    @FXML
    private TableView<Room> table_hotelR2;

    private String[] resVals; //HotelID, startDate, endDate
    private int currResID; //currResID

    public void fillChooseRoomTable(String[] values){
        resVals = values;
        currResID = Integer.parseInt(values[3]);

        col_roomidR.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomID"));
        col_bedtypeR.setCellValueFactory(new PropertyValueFactory<Room, String>("bedType"));
        col_roompriceR.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomPrice"));

       // System.out.println(Arrays.toString(vals));

        //Values are passed in from adminHomeController
        table_hotelR2.setItems(Functions.populateAvailableRooms(values[0], values[1], values[2]));
    }

        /**
     * Maybe here check for account type or should it ask before? I think it should check
     * This Function will let the user select the room and then reserve the room.
     * Must provide a valid email and room
     */

    @FXML
    private void reserveButton(ActionEvent event) throws IOException {
        System.out.println("INSIDE reserveButton");
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User u = (User) stage.getUserData();

        int priceTotal = 0;

        try {
            //Error check user inputs
            if (emailR.getText().isEmpty()) {  //|| if email is invalid?
                System.out.println("Please enter a valid email\n");
                throw new Exception();
            } else if (roomR.getText().isEmpty()) { //|| ?
                System.out.println("Please enter a valid Room\n");
                throw new Exception();
            }

            //Check if customer is logged in
            if (u.getAccountType() == 0 && !emailR.getText().equals(u.getEmail()))
                System.out.println("Email does not match logged account type");
            else {
                Connection connectDB = DBConnection.getConnection();

                //WE delete old reservation!
                System.out.println("RESERVATION ID: " + currResID);
                String deleteQuery = "DELETE FROM hotel_db.Reservation WHERE reservationId = " + currResID;
                String getPrice = "SELECT roomPrice, weekendRate FROM hotel_db.Room as Room INNER JOIN hotel_db.Hotel as Hotel ON Room.Hotel_hotelID = Hotel.hotelID WHERE roomID = " + roomR.getText();

                PreparedStatement ps = connectDB.prepareStatement(deleteQuery);
                ps.executeUpdate();


                //Calculate final price with differential
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate startDT = LocalDate.parse(resVals[1], dtf);
                LocalDate endDT = LocalDate.parse(resVals[2], dtf);
                int numOfDays = (int) ChronoUnit.DAYS.between(startDT, endDT);

                DayOfWeek day1 = DayOfWeek.of(startDT.get(ChronoField.DAY_OF_WEEK));
                DayOfWeek day2 = DayOfWeek.of(endDT.get(ChronoField.DAY_OF_WEEK));

                ResultSet rs = ps.executeQuery(getPrice);
                rs.next();
                if (day1 == DayOfWeek.SATURDAY || day1 == DayOfWeek.SUNDAY || day2 == DayOfWeek.SATURDAY || day2 == DayOfWeek.SUNDAY) {
                    //Retrieve roomPrice and weekendRate
                    System.out.println("Weekend detected");
                    priceTotal = (int) (numOfDays * rs.getInt(1) * (1 + rs.getDouble(2) / 100));
                    successLabel.setText("Success! \n\nRoom price = " + rs.getInt(1) + "\nNumber of nights = " + numOfDays + "\nWeekend Rate = " + (1 + rs.getDouble(2) / 100) + "\n\nTotal Price = " + priceTotal);

                }
                else{
                    priceTotal = numOfDays * rs.getInt(1);
                    successLabel.setText("Success! \n\nRoom price = " + rs.getInt(1) + "\nNumber of nights = " + numOfDays + "\nWeekend Rate = 0" + "\n\nTotal Price = " + priceTotal);

                }

                System.out.println("total price= " + priceTotal);


                //Insert the reservation to DB
                Reservation reservation = new Reservation(-1, Integer.parseInt(roomR.getText()), resVals[1], resVals[2], emailR.getText(), true, priceTotal);
                Functions.createReservation(reservation);

                //ADD A LABEL FOR SUCCESS OR FAILURE HERE

                emailR.clear();
                roomR.clear();
                fillChooseRoomTable(resVals);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        /******************************************* BACK BUTTON **********************************************/
    @FXML
    private void returnButton(ActionEvent event) throws IOException {
            System.out.println("Returning to Home Admin");


            try {
                //Stage stage;
                Scene scene;
                Parent root;

                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                User u = (User) stage.getUserData();

                if(u.getAccountType() == 1)
                    root = FXMLLoader.load((getClass().getResource("/styles/adminHome.fxml")));
                else
                    root = FXMLLoader.load((getClass().getResource("/styles/customerHome.fxml")));

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
}