package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.database.Database;

import java.sql.*;

public class LoginDAO {
    private Connection connection;

    public LoginDAO() {
        this.connection = Database.ConnectToDatabase();
    }

    public boolean authenticate(Login login) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login.getEmail());
            stmt.setString(2, login.getPassword());

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
