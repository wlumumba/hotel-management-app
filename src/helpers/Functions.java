package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Functions {

    /*** Returns a List of all Hotels using SQL ***/
    public static ObservableList<Hotel> populateHotelTable(){
        ObservableList<Hotel> hotelList = FXCollections.observableArrayList();

        Connection connectDB = DBConnection.getConnection();
        String selectQuery = "SELECT * FROM hotel_db.Hotel";

        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                hotelList.add(new Hotel(rs.getInt("hotelID"), rs.getString("hotelName"), rs.getString("hotelType"), rs.getString("amenities"), rs.getInt("maxRooms"), rs.getInt("standardPrice"), rs.getInt("queenPrice"), rs.getInt("kingPrice"), rs.getInt("weekendRate")));
            }

        } catch (Exception e){
            System.out.println(e);
        }

        return hotelList;
    }

    /**** Fill choose room Table ****/
    public static ObservableList<Room> populateAvailableRooms(String hotelID, String startDate, String endDate){
        ObservableList<Room> roomsList = FXCollections.observableArrayList();

        Connection connectDB = DBConnection.getConnection();

        //Remove all rooms that are booked during those dates for that hotel
        String selectQuery = "select * from hotel_db.Room where roomID not in " +
        "(select Room_roomID from hotel_db.Reservation where ? <= endDate and ? >= startDate) "+
        "and Hotel_hotelID = ? " +
        "order by bedType";

        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ps.setString(1, endDate);
            ps.setString(2, startDate);
            ps.setInt(3, Integer.parseInt(hotelID));

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                roomsList.add(new Room(rs.getInt("roomID"), rs.getString("bedType"), rs.getInt("roomPrice"), rs.getInt("Hotel_hotelID")));
            }
            System.out.println(roomsList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return roomsList;

    }


    /*** Create reservation SQL ***/
    public static void createReservation(Reservation r1)
    {
        Connection connectDB = DBConnection.getConnection();
        //INSERT INTO `hotel_db`.`Reservation` (`reservationID`, `startDate`, `endDate`, `status`, `Room_roomID`, `Room_Hotel_hotelID`, `User_email`) VALUES ('1', '2', '3', '4', '5', '6', '7');
        String insertQuery = "INSERT INTO hotel_db.Reservation (reservationID, startDate, endDate, status, Room_roomID, User_email) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Add a if statement to check if their are duplicates such as similar date or room ID possibly?
        try{
            System.out.println(r1);
            PreparedStatement st = connectDB.prepareStatement(insertQuery);
            st.setInt(1, r1.getReservationID());
            st.setString(2, r1.getStartDate());
            st.setString(3, r1.getEndDate());
            st.setBoolean(4, true);
            st.setInt(5, r1.getRoomID());
            st.setString(6, r1.getUserEmail());

            //UPDATE hotel_db.Hotel SET hotelID = '', hotelName = '', hotelType = '', amenities = '', maxRooms = '', standardPrice = '', queenPrice = '', kingPrice = '' WHERE (hotelID = '');

            st.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
