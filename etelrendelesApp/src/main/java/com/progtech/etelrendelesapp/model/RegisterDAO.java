package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.database.Database;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;

public class RegisterDAO {
    private Connection connection;

    public RegisterDAO() {
        this.connection = Database.ConnectToDatabase();
    }

    public RegisterDAO(Connection connection) {
        this.connection = connection;
    }

    public void registerUser(User user) {
        try {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)"
            );
            stmt.setString(1, user.getFirst_name());
            stmt.setString(2, user.getLast_name());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, hashedPassword);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emailExists(String email) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT email FROM user WHERE email = ?"
        );
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next();
        rs.close();
        stmt.close();
        return exists;
    }

}
