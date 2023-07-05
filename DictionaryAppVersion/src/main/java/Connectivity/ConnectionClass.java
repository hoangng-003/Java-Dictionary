package Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    /**
     * Config connection.
     */
    public Connection getconnection() throws ClassNotFoundException, SQLException {
        String dbName = "dictionary";
        String userName = "root";
        String passWord = "";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, passWord);
    }
}
