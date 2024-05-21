package com.progtech.etelrendelesapp.model;

import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterDAOTest {

    private Connection connection;
    private RegisterDAO registerDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        registerDAO = new RegisterDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    public void testRegisterUser() throws SQLException {
        User user = new User("Example", "User", "test@example.com", "password");
        registerDAO.registerUser(user);

        assertTrue(registerDAO.emailExists("test@example.com"));
    }

    @Test
    public void testEmailExists() throws SQLException {
        assertFalse(registerDAO.emailExists("nonexistent@example.com"));

        User user = new User("Example", "User", "test@example.com", "password");
        registerDAO.registerUser(user);

        assertTrue(registerDAO.emailExists("test@example.com"));
    }
}
