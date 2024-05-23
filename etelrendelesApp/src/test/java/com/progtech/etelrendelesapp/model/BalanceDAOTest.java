package com.progtech.etelrendelesapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BalanceDAOTest {

    private Connection connection;
    private BalanceDAO balanceDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        balanceDAO = new BalanceDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    void updateBalanceInDatabase() throws SQLException {
        User testUser = new User("Test", "User", "test@test.com");
        int newBalance = 200;

        balanceDAO.updateBalanceInDatabase(testUser, newBalance);

        try (PreparedStatement pstmt = connection.prepareStatement(
                "SELECT balance FROM user WHERE email = ?")) {
            pstmt.setString(1, testUser.getEmail());
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(newBalance, rs.getInt("balance"));
            }
        }
    }
}
