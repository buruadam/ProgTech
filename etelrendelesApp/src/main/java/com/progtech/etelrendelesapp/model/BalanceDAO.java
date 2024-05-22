package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.model.User;
import com.progtech.etelrendelesapp.controller.BalanceController;
import com.progtech.etelrendelesapp.database.Database;
import com.progtech.etelrendelesapp.helper.AlertHelper;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalanceDAO {
    private Connection connection;

    public BalanceDAO() {
        this.connection = Database.ConnectToDatabase();
    }

    public BalanceDAO(Connection connection) {
        this.connection = connection;
    }

    public void updateBalanceInDatabase(User user, int newBalance) throws SQLException {
        String sql = "UPDATE user SET balance = ? WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();
        }
    }
}
