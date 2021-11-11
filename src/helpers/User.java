package helpers;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.sql.*;

public class User {

    //User attribute variables
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int accountType; // 1 for admin, 0 for customer

    //Default constructor
    public User(String userName, String firstName, String lastName, String email, String password, int accountType) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    //Getters
    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User " + userName + " " + firstName + " " + lastName + " " + email + " " + password + " " + accountType;
    }

    //returns 0 if fails, 2 if username is taken and returns one if it succeeds
    public static int createAdmin(User admin){
        Connection connectDB = DBConnection.getConnection(); //Below is it hotel_db.Admin?
        String insertQuery = "INSERT INTO hotel_db.User (username, firstname, lastname, email, password, accType) VALUES (?, ?, ?, ?, ?, 1)";

        try {
            //Create statement, fill empty fields
            PreparedStatement statement = connectDB.prepareStatement(insertQuery);
            statement.setString(1,admin.getUserName());
            statement.setString(2,admin.getFirstName());
            statement.setString(3,admin.getLastName());
            statement.setString(4,admin.getEmail());
            statement.setString(5,admin.getPassword());
            statement.executeUpdate();

            System.out.println("Success Inserted " + admin.toString());
            return 1;

        }
        catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Username taken! Try another one");
            return 2;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: Failed to insert to DB");
            return 0;
        }
    }

    public static User getUserFromEmail(String email){

        Connection connectDB = DBConnection.getConnection(); //Below is it hotel_db.Admin?
        //Make query to find number of rows in a table
        String query = "select * from hotel_db.User where email=\""+ email + "\"";

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                User user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
                return user;
            }


        } catch (Exception e){
            System.out.println(e);
        }

        return new User("user name", "first name", "last name", "email", "password", 3);
    }
//returns 0 if fails, 1 if user is found and updated
    public static int updateUser(User user){

        Connection connectDB = DBConnection.getConnection();
        //Make query to find number of rows in a table
        String username = user.getUserName();
        String fname = user.getFirstName();
        String lname = user.getLastName();
        String password = user.getPassword();
        String email = user.getEmail();
        int accType = user.accountType;



        String query = "UPDATE hotel_db.User SET username = \"" + username +"\", firstname = \"" + fname + "\", lastname = \"" + lname +"\", password = \""+ password +"\", accType = " + accType + " WHERE email = \"" + user.getEmail() + "\"";
        System.out.println(query);

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            int count = ps.executeUpdate();
            return 1;




        } catch (Exception e){
            System.out.println(e);
            return 0;
        }




    }
}
