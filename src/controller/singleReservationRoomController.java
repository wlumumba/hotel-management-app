
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class singleReservationRoomController implements Initializable {

    //Method called when screen is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChooseRoomTable();
    }



    /************************************* CHOOSE A ROOM TAB *****************************************/

    @FXML
    private TextField roomR;
    @FXML
    private TextField emailR;
    @FXML
    private TextField startDate; //not sure about these yet
    @FXML
    private TextField endDate; //not sure about these yet

    @FXML
    private TableColumn<Room, Integer> col_roomidR;
    @FXML
    private TableColumn<Room, String> col_bedtypeR;
    @FXML
    private TableColumn<Room, Integer> col_roompriceR;
    @FXML
    private TableView<Room> table_rooms;

    public void fillChooseRoomTable(){
        col_roomidR.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomID"));
        col_bedtypeR.setCellValueFactory(new PropertyValueFactory<Room, String>("bedType"));
        col_roompriceR.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomPrice"));

        //Must figure out how to pass in values from other controller!
        table_rooms.setItems(Functions.populateAvailableRooms(-1, -1, -1));
    }


    /**
     * Maybe here check for account type or should it ask before? I think it should check
     * This Function will let the user select the room and then reserve the room.
     * Must provide a valid email and room
     */
    @FXML
    private void reserveButton(ActionEvent event) throws IOException  {
        System.out.println("Reserve Room Button");
        //  try {
        if (emailR.getText().isEmpty()) {  //|| if email is inavlid?
            System.out.println("Please enter a valid email\n");
           // warningLabel.setText("Enter a valid email");
        } else if (roomR.getText().isEmpty()) { //|| ?
            System.out.println("Please enter a valid Room\n");
            //warningLabel.setText("Enter a valid Room");
        }
        //MAYBE ASK USER TO SELECT FROM CALENDAR OR THE USER MUST PUT START DATE IN MONTH/DAY
        //THEN END DATE MONTH/DAY AND THEN HAVE A DATABASE/RESERVATION FOR THE USER FOR THAT ROOM?
        //THIS WILL HAVE TO CHECK IF ROOM IS AVAILABLE IF TRUE ELSE DON'T DISPLAY AND WAIT UNTIL ROOM IS AVAILABLE?
        //DOES THIS HAVE TO BE IN REAL TIME?

        //   }
       /* catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in ReserveButton");
        }*/

    }

    /******************************************* BACK BUTTON **********************************************/
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
}