package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public Connection dblink;

    public Connection getConnection(){
        String dbuser = "admin";
        String dbpassword = "wa0T0WQF0Aip59H3G5K1";
        String url = "jdbc:mysql://hoteldb.cijrtaptpxkl.us-east-1.rds.amazonaws.com:3306";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink = DriverManager.getConnection(url, dbuser, dbpassword);

        } catch (Exception e){
            e.printStackTrace();
        }

        return dblink;
    }
}
