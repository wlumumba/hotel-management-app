package helpers;

public class Room {
    private int roomID, roomPrice, hotelID;
    private String bedType;

    public Room(int roomID, String bedType, int roomPrice, int hotelID) {
        this.roomID = roomID;
        this.roomPrice = roomPrice;
        this.hotelID = hotelID;
        this.bedType = bedType;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public int getHotelID() {
        return hotelID;
    }

    public String getBedType() {
        return bedType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomPrice=" + roomPrice +
                ", hotelID=" + hotelID +
                ", bedType='" + bedType + '\'' +
                '}';
    }
}
