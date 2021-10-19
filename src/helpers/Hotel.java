package helpers;

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
}
