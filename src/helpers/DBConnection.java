package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static Connection dblink;

    public static Connection getConnection(){
        String dbuser = "admin";
        String dbpassword = "wa0T0WQF0Aip59H3G5K1";
        String url = "jdbc:mysql://hoteldb.cijrtaptpxkl.us-east-1.rds.amazonaws.com:3306";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink = DriverManager.getConnection(url, dbuser, dbpassword);

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("DB Connect FAILED");
        }

        return dblink;
    }

    public static void createReservation(Reservation r1)
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

    }
}
