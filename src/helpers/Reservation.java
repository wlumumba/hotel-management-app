package helpers;

public class Reservation {

    //Instance variables
    private int reservationID, roomID;
    String startDate, endDate, userEmail;
    boolean status;

    //Default constructor
    public Reservation(int reservationID, int roomID, String startDate, String endDate, String userEmail, boolean status) {
        this.reservationID = reservationID;
        this.roomID = roomID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userEmail = userEmail;
        this.status = status;
    }

    //Get methods
    public int getReservationID() {
        return reservationID;
    }
    public int setReservationID(int reservationID) {
        return reservationID;
    }

    public int getRoomID() {
        return roomID;
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
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", status=" + status +
                '}';
    }
}
