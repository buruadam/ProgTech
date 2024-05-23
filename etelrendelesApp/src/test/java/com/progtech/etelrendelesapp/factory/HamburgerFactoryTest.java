package com.progtech.etelrendelesapp.factory;

import com.progtech.etelrendelesapp.model.BasicFood;
import com.progtech.etelrendelesapp.model.HSQLDBTestDatabase;
import com.progtech.etelrendelesapp.model.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class HamburgerFactoryTest {

    private Connection connection;
    private HamburgerFactory hamburgerFactory;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        hamburgerFactory = new HamburgerFactory();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    void createMenu() {
        Menu menu = hamburgerFactory.createMenu("Hamburger");
        assertNotNull(menu);
        assertTrue(menu instanceof BasicFood);
        BasicFood food = (BasicFood) menu;
        assertEquals("Hamburger", food.getName());
        assertEquals(1800, food.getPrice());
    }
}