package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;

public class loginController {

    @FXML
    private TextField userNameLog;

    @FXML
    private TextField passwordLog;

    @FXML
    private Button adminButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button createButton;

    @FXML
    private TextField userNameCreate;

    @FXML
    private TextField passwordCreate;



    @FXML
    public void adminButtonClicked(Event e){
        System.out.println("admin login");
    }

    @FXML
    public void customerButtonClicked(Event e){
        System.out.println("customer login");
    }

    @FXML
    public void createButtonClicked(Event e){
        System.out.println("create login");
        String username = userNameCreate.getText();
        String password = passwordCreate.getText();
        userNameCreate.clear();
        passwordCreate.clear();

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
            reader = new BufferedReader(new FileReader("C:\\Users\\Aaron Gingrich\\OneDrive - Triada\\Desktop\\hotelManagmentApp\\src\\sample\\users.txt"));
            String line = reader.readLine();
            while (line != null) {
               // System.out.println(line);
                String[] arrOfStr = line.split(" ");

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

    public static void addUser(String userName, String password){
        String append = "0 " + userName + " " + password + "\n";

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("C:\\Users\\Aaron Gingrich\\OneDrive - Triada\\Desktop\\hotelManagmentApp\\src\\sample\\users.txt", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.println(append);

            System.out.println("Data Successfully appended into file");
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException io) {// can't do anything }
            }



    }

}
}