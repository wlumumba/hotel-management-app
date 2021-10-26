package helpers;

public class Reservation {

    //Instance variables
    private int reservationID, roomID, hotelID;
    String startDate, endDate, userEmail;
    boolean status;

    //Default constructor
    public Reservation(int reservationID, int roomID, int hotelID, String startDate, String endDate, String userEmail, boolean status) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.hotelID = hotelID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userEmail = userEmail;
        this.status = status;
    }

    //Get methods


    public int getReservationID() {
        return reservationID;
    }

    public int getRoomID() {
        return roomID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Reservation {" +
                "reservationID=" + reservationID +
                ", roomID=" + roomID +
                ", hotelID=" + hotelID +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", status=" + status +
                '}';
    }
}
