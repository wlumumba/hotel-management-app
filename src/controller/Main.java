package controller;
/**
 * GROUP 10 PROJECT
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/styles/adminHome.fxml"));
        primaryStage.setTitle("Sleepless Nights B&B");
        primaryStage.setScene(new Scene(root, 754, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
