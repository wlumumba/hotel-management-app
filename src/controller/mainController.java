package controller;

import helpers.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mainController {

    @FXML
    private Label dataLabel;

    public void connectButton(ActionEvent event){
        DBConnection connectNow = new DBConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT username FROM hotel_db.test";

        try{
            Statement st = connectDB.createStatement();
            ResultSet queryOutput = st.executeQuery(connectQuery);

            while (queryOutput.next()){
                dataLabel.setText(queryOutput.getString("username"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
