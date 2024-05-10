package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.database.Database;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;

public class LoginDAO {
    private Connection connection;

    public LoginDAO() {
        this.connection = Database.ConnectToDatabase();
    }

    public boolean authenticate(User user) {
        String query = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                return BCrypt.checkpw(user.getPassword(), storedHashedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
