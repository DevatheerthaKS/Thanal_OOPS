package com.thanal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/thanal";
    private static final String USER = "root";
    private static final String PASSWORD = "24cs331@deva";

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" Connected to Thanal Database Successfully!");
            }
        } catch (SQLException e) {
            System.out.println(" Database connection error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(" JDBC Driver not found!");
        }
        return connection;
    }
}
