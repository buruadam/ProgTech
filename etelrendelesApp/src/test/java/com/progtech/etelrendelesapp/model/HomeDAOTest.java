package com.progtech.etelrendelesapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class HomeDAOTest {
    private Connection connection;
    private HomeDAO homeDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        homeDAO = new HomeDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }
    @Test
    void updateBalanceInDatabaseTest() throws SQLException {
        String email = "test@test.com";
        int newBalance = 100;

        homeDAO.updateBalanceInDatabase(email, newBalance);

        PreparedStatement psmt = connection.prepareStatement("SELECT balance FROM user WHERE email = ?");
        psmt.setString(1, email);
        ResultSet rs = psmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(newBalance, rs.getInt("balance"));
    }

    @Test
    void insertOrderTest() throws SQLException {
        String email = "test@test.com";
        int price = 500;
        homeDAO.insertOrder(email,price);
        PreparedStatement psmt = connection.prepareStatement("SELECT user_email, total_price FROM orders WHERE user_email = ?");
        psmt.setString(1, email);
        ResultSet rs = psmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(price, rs.getInt("total_price"));
        assertEquals(email, rs.getString("user_email"));


    }

    @Test
    void loadBalanceFromDatabaseTest() throws SQLException {
        String email = "test@test.com";
        Integer balance = homeDAO.loadBalanceFromDatabase(email);
        assertNotNull(balance);
        assertEquals(balance, 100);
    }
}