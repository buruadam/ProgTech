package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeDAO {
    private Connection connection;

    public HomeDAO(){this.connection = Database.ConnectToDatabase();}

    public HomeDAO(Connection connection) { this.connection = connection;}

    public void updateBalanceInDatabase(String email, int newBalance) throws SQLException {
        String sql = "UPDATE user SET balance = ? WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }
    public void insertOrder(String email, double price) throws SQLException {
        String sql = "INSERT INTO orders (user_email, total_price) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        }
    }
    public Integer loadBalanceFromDatabase(String email) throws SQLException {
        String sql = "SELECT balance FROM user WHERE email = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, email);
            ResultSet rs = psmt.executeQuery();
            if (rs.next())
                return rs.getInt("balance");
        }
        return null;
    }
}
