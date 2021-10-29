package helpers;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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

    public static void createAdmin(User admin){
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

        }
        catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Username taken! Try another one");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: Failed to insert to DB");
        }
    }
}
