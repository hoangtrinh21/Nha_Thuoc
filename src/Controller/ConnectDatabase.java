package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {
    public static String jdbcURL = "jdbc:mysql://localhost:3306/NhaThuoc?autoReconnect=true&useSSL=false";
    public static String username = "root";
    public static String password = "12345678";

    public static Connection getConnection(String dbURL, String userName,
                                           String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
