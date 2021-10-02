package helpers;

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
}
