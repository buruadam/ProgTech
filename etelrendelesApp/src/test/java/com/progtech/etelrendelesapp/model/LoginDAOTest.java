package com.progtech.etelrendelesapp.model;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDAOTest {

    private Connection connection;
    private LoginDAO loginDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        loginDAO = new LoginDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    public void testAuthenticateValidUser() {
        User validUser = new User("test@test.com", "validpassword");
        assertTrue(loginDAO.authenticate(validUser));
    }

    @Test
    public void testAuthenticateInvalidUser() {
        User invalidUser = new User("test@test.com", "wrongpassword");
        assertFalse(loginDAO.authenticate(invalidUser));
    }

    @Test
    public void testAuthenticateNonExistentUser() {
        User nonExistentUser = new User("nonexistent@test.com", "password");
        assertFalse(loginDAO.authenticate(nonExistentUser));
    }
}
