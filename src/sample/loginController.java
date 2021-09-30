package sample;

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

            /////////////

            //////////
            root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
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

        root = FXMLLoader.load(getClass().getResource("guestHome.fxml"));
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

        if(validateUser(0, userName, password)) {
            System.out.println("Access granted");

            Stage stage;
            Scene scene;
            Parent root;

            root = FXMLLoader.load(getClass().getResource("customerHome.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }
        else
            System.out.println("Incorrect loggin info, please try again");
    }

    @FXML
    public void createButtonClicked(Event e) throws IOException {
        //System.out.println("create login");
        String username = userNameCreate.getText();
        String password = passwordCreate.getText();
        userNameCreate.clear();
        passwordCreate.clear();

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
            reader = new BufferedReader(new FileReader("src\\sample\\user.txt"));
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

        File file = new File("src\\sample\\user.txt");
        FileWriter fr = new FileWriter(file, true);
        fr.write(append);
        fr.close();

}

    public static boolean validateUser(int accountType, String userName, String password){

        boolean exist = false;
        BufferedReader reader;
        int accType;
        try {
            reader = new BufferedReader(new FileReader("src\\sample\\user.txt"));
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