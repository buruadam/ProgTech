package com.progtech.etelrendelesapp.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection ConnectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/etelrendeles";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
