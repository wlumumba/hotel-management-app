package helpers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Functions {
   /* public static void createReservation(Reservation r1)
    {
        Connection connectDB = DBConnection.getConnection();
        //INSERT INTO `hotel_db`.`Reservation` (`reservationID`, `startDate`, `endDate`, `status`, `Room_roomID`, `Room_Hotel_hotelID`, `User_email`) VALUES ('1', '2', '3', '4', '5', '6', '7');
        String insertQuery = "INSERT INTO hotel_db.Reservation (reservationID, startDate, endDate, status, Room_roomID, Room_Hotel_hotelID, User_email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            System.out.println(r1);
            PreparedStatement st = connectDB.prepareStatement(insertQuery);
            st.setInt(1, r1.getReservationID());
            st.setString(2, r1.getStartDate());
            st.setString(3, r1.getEndDate());
            st.setBoolean(4, true);
            st.setInt(5, r1.getRoomID());
            st.setInt(6, r1.getHotelID());
            st.setString(7, r1.getUserEmail());

            //UPDATE hotel_db.Hotel SET hotelID = '', hotelName = '', hotelType = '', amenities = '', maxRooms = '', standardPrice = '', queenPrice = '', kingPrice = '' WHERE (hotelID = '');

            st.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }*/

    //Create reservation
    public static void createReservation(Reservation r1)
    {
        Connection connectDB = DBConnection.getConnection();
        //INSERT INTO `hotel_db`.`Reservation` (`reservationID`, `startDate`, `endDate`, `status`, `Room_roomID`, `Room_Hotel_hotelID`, `User_email`) VALUES ('1', '2', '3', '4', '5', '6', '7');
        String insertQuery = "INSERT INTO hotel_db.Reservation (reservationID, startDate, endDate, status, Room_roomID, Room_Hotel_hotelID, User_email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Add a if statement to check if their are duplicates such as similar date or room ID possibly?
        try{
            System.out.println(r1);
            PreparedStatement st = connectDB.prepareStatement(insertQuery);
            st.setInt(1, r1.getReservationID());
            st.setString(2, r1.getStartDate());
            st.setString(3, r1.getEndDate());
            st.setBoolean(4, true);
            st.setInt(5, r1.getRoomID());
            st.setInt(6, r1.getHotelID());
            st.setString(7, r1.getUserEmail());

            //UPDATE hotel_db.Hotel SET hotelID = '', hotelName = '', hotelType = '', amenities = '', maxRooms = '', standardPrice = '', queenPrice = '', kingPrice = '' WHERE (hotelID = '');

            st.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /*@FXML
    public void searchButton(ActionEvent event) throws IOException {
        System.out.println("Reservation Search Button Clicked");
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load((getClass().getResource("/styles/searchReservation.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene((root));
        stage.setScene((scene));
        stage.show();
    }*/







}
