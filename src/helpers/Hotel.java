package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Hotel {

    //Hotel attribute variables
    private int hotelID;
    private String hotelName, hotelType, amenities;
    private int maxRooms, standardPrice, queenPrice, kingPrice, weekendRate;

    //Default constructor
    public Hotel(int hotelID, String hotelName, String hotelType, String amenities, int maxRooms, int standardPrice, int queenPrice, int kingPrice, int weekendRate) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelType = hotelType;
        this.amenities = amenities;
        this.maxRooms = maxRooms;
        this.standardPrice = standardPrice;
        this.queenPrice = queenPrice;
        this.kingPrice = kingPrice;
        this.weekendRate = weekendRate;
    }

    //Getters
    public int getHotelID() {
        return hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelType() {
        return hotelType;
    }

    public String getAmenities() {
        return amenities;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public int getStandardPrice() {
        return standardPrice;
    }

    public int getQueenPrice() {
        return queenPrice;
    }

    public int getKingPrice() {
        return kingPrice;
    }

    public int getWeekendRate() {
        return weekendRate;
    }

    @Override
    public String toString() {
        return "Hotel " + hotelID + " " + hotelName + " " + hotelType + " " + amenities + " " + maxRooms + " " + standardPrice + " " + queenPrice + " " + kingPrice + " " + weekendRate;
    }

    public static Hotel getHotelFromName(String hotelName){

        Connection connectDB = DBConnection.getConnection(); //Below is it hotel_db.Admin?
        //Make query to find number of rows in a table
        String query = "select * from hotel_db.Hotel where hotelName=\""+ hotelName + "\"";

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Hotel hotel = new Hotel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
                return hotel;
                }


        } catch (Exception e){
            System.out.println(e);
        }

        return new Hotel(22, "hotel name", "Hotel Type", "amen", 55, 55, 55, 55, 15);
    }

    public static void updateHotel(Hotel hotel){

        Connection connectDB = DBConnection.getConnection();
        //Make query to find number of rows in a table
        String hotelType = hotel.getHotelType();
        String amenities = hotel.getAmenities();
        int stdPrice = hotel.getStandardPrice();
        int qPrice = hotel.getQueenPrice();
        int kPrice = hotel.getKingPrice();
        int weekendRate = hotel.getWeekendRate();

        String query = "UPDATE hotel_db.Hotel SET hotelType = \"" + hotelType +"\", amenities = \"" + amenities + "\", standardPrice = " + stdPrice +", queenPrice = "+ qPrice +", kingPrice = " + kPrice + ", weekendRate = " + weekendRate + " WHERE hotelName = \"" + hotel.getHotelName()+"\"";
        System.out.println(query);

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            ps.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }


    }
}
