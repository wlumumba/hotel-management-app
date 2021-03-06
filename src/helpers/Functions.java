package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Functions {

/********************************ADMIN HOME*************************************************/
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
        "(select Room_roomID from hotel_db.Reservation where ? <= endDate and ? >= startDate) "+ //and is active
        "and Hotel_hotelID = ? " +
        "order by bedType";

        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setInt(3, Integer.parseInt(hotelID));

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                roomsList.add(new Room(rs.getInt("roomID"), rs.getString("bedType"), rs.getInt("roomPrice"), rs.getInt("Hotel_hotelID")));
            }
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
        String insertQuery = "INSERT INTO hotel_db.Reservation (startDate, endDate, status, Room_roomID, User_email, finalPrice) VALUES (?, ?, ?, ?, ?, ?)";

        try{
            System.out.println(r1);
            PreparedStatement st = connectDB.prepareStatement(insertQuery);
            //st.setInt(1, r1.getReservationID());
            st.setString(1, r1.getStartDate());
            st.setString(2, r1.getEndDate());
            st.setBoolean(3, true);
            st.setInt(4, r1.getRoomID());
            st.setString(5, r1.getUserEmail());
            st.setInt(6, r1.getFinalPrice());

            //UPDATE hotel_db.Hotel SET hotelID = '', hotelName = '', hotelType = '', amenities = '', maxRooms = '', standardPrice = '', queenPrice = '', kingPrice = '' WHERE (hotelID = '');

            st.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException ex) {
            createAccount(r1.getUserEmail());
            createReservation(r1);
            System.out.println("Account not found, creating account then re-calling creating reservation");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void createAccount(String email){


        Connection connectDB = DBConnection.getConnection(); //Below is it hotel_db.Admin?
        String insertQuery = "INSERT INTO hotel_db.User (username, firstname, lastname, email, password, accType) VALUES (?, ?, ?, ?, ?, 0)";

        try {
            //Create statement, fill empty fields
            PreparedStatement statement = connectDB.prepareStatement(insertQuery);
            statement.setString(1,email);
            statement.setString(2,"DUMMY");
            statement.setString(3,"DUMMY");
            statement.setString(4,email);
            statement.setString(5,"password");
            statement.executeUpdate();

            //System.out.println("Success Inserted " + admin.toString());

        }
        catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Username taken! Try another one");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: Failed to insert to DB");
        }

    }


/***Meant to get the reservations to a list**/
    public static ObservableList<Reservation> getNewReservationList(){
        //MAYBE LINE 129
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        System.out.println("INSIDE getNewReservationListT ");

        Connection connectDB = DBConnection.getConnection();

        String selectQuery = "SELECT * FROM hotel_db.Reservation";

        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();


           // System.out.println("INSIDE TRY RESERVATIONLIST ");
            if (rs.next()) {
                do {
               //     System.out.println("INSIDE DO RESERVATIONLIST ");
                    reservationList.add(new Reservation(rs.getInt(1),rs.getInt(5), rs.getString(2),rs.getString(3), rs.getString(6),rs.getBoolean(4), rs.getInt(7)));
                } while (rs.next());
            }
            else
            {
                System.out.println("WE NEED RESERVATIONS. NO RESERVATIONS FOUND");
            }

        } catch (Exception e){
            System.out.println(e);
        }

        return reservationList;
    }
    /***Meant to get the rooms to a list**/
    public static ObservableList<Room> getNewRoomList(){
        //MAYBE LINE 129
        ObservableList<Room> roomList = FXCollections.observableArrayList();

        System.out.println("INSIDE getNewRoomListT ");

        Connection connectDB = DBConnection.getConnection();

        String selectQuery = "SELECT * FROM hotel_db.Room";
       // int counter = 0;
       // int counter1 = 0;
        try {
            PreparedStatement ps = connectDB.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();


           // System.out.println("INSIDE TRY roomlist ");
          if (rs.next()) { //changed from next() . next works though
                do {
                //    System.out.println("INSIDE DO roomlist ");
                    roomList.add(new Room(rs.getInt(1),rs.getString(2), rs.getInt(3),rs.getInt(4)));
                } while (rs.next());
            }
            else
            {
                System.out.println("NO ROOMS FOUND");
            }
            // System.out.println("INSIDE TRY roomlist ");
        } catch (Exception e){
            System.out.println(e);
        }

        return roomList;
    }
    /***********************************************************************************************/
}
