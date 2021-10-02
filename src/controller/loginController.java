package controller;

import helpers.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class loginController {

    User user = new User();

    @FXML
    private TextField userNameLog;

    @FXML
    private TextField passwordLog;

    @FXML
    private Button adminButton;

    @FXML
    private Button guestButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField userNameCreate;

    @FXML
    private TextField passwordCreate;

    @FXML
    private TextField firstNameCreate;

    @FXML
    private TextField lastNameCreate;

    @FXML
    private TextField emailCreate;



    @FXML
    public void adminButtonClicked(Event e) throws IOException {

        System.out.println("admin login");

        String userName = userNameLog.getText();
        String password = passwordLog.getText();
        userNameLog.clear();
        passwordLog.clear();

        if(validateUser(1, userName, password)) {
            System.out.println("Access granted");
            Stage stage;
            Scene scene;
            Parent root;


            root = FXMLLoader.load(getClass().getResource("/styles/adminHome.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
            System.out.println("Incorrect loggin info, please try again");
    }

    public void guestButtonClicked(Event e) throws IOException {

        System.out.println("guest login");
        Stage stage;
        Scene scene;
        Parent root;

        root = FXMLLoader.load(getClass().getResource("/styles/guestHome.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void customerButtonClicked(Event e) throws IOException {
        System.out.println("customer login");
        //System.out.println("admin login");

        String userName = userNameLog.getText();
        String password = passwordLog.getText();
        userNameLog.clear();
        passwordLog.clear();


        user.setUserName(userName);
        user.setPassword(password);



        if(validateUser(0, userName, password)) {
            System.out.println("Access granted");

            Node node = (Node) e.getSource();
            // Step 3

            Stage stage = (Stage) node.getScene().getWindow();

            stage.close();


                // Step 4

                Parent root = FXMLLoader.load(getClass().getResource("/styles/customerHome.fxml"));
                // Step 5

                stage.setUserData(user);

                // Step 6
                Scene scene = new Scene(root);
                stage.setScene(scene);
                // Step 7
                stage.show();

            /*
            Stage stage;
            Scene scene;
            Parent root;

            root = FXMLLoader.load(getClass().getResource("customerHome.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            */

        }
        else
            System.out.println("Incorrect loggin info, please try again");
    }

    @FXML
    public void createButtonClicked(Event e) throws IOException {
        //System.out.println("create login");
        String username = userNameCreate.getText();
        String password = passwordCreate.getText();
        String firstName = firstNameCreate.getText();
        String lastName = lastNameCreate.getText();
        String email = emailCreate.getText();
        int accountType = 0;

        user.setEmail(email);
        user.setUserName(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);

        userNameCreate.clear();
        passwordCreate.clear();
        firstNameCreate.clear();
        lastNameCreate.clear();
        emailCreate.clear();

        //System.out.println(user.getAccountType() + " " + user.getEmail() + " " + user.getUserName()  + " " + user.getFirstName() + " " + user.getEmail() + " " + user.getLastName() + " " + user.getPassword());


        //System.out.println("test 1");

        if(username.isEmpty() || password.isEmpty())
            System.out.println("Please enter password and username");

        else if (userExist(username))
            System.out.println("username already exist, try again");
        else
            addUser(username, password);





        //System.out.println(username + " x " + password);


    }

    public static boolean userExist(String userName){
        boolean exist = false;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/controller/user.txt"));
            String line = reader.readLine();
            while (line != null) {
               // System.out.println(line);
                String[] arrOfStr = line.split(",");

                if(arrOfStr[1].equals(userName)) {
                    exist = true;

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static void addUser(String userName, String password) throws IOException {
        String append = "0," + userName + "," + password + "\n";
       // System.out.println("test 1");

        File file = new File("src/controller/user.txt");
        FileWriter fr = new FileWriter(file, true);
        fr.write(append);
        fr.close();

}

    public static boolean validateUser(int accountType, String userName, String password){

        boolean exist = false;
        BufferedReader reader;
        int accType;
        try {
            reader = new BufferedReader(new FileReader("src/controller/user.txt"));
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                String[] arrOfStr = line.split(",");
                accType = Integer.parseInt(arrOfStr[0]);

                if(accType == accountType && arrOfStr[1].equals(userName) && arrOfStr[2].equals(password)) {
                    exist = true;

                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return exist;


    }
}