package com.progtech.etelrendelesapp.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuDAOTest {

    private Connection connection;
    private MenuDAO menuDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        menuDAO = new MenuDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    void getAllFood() throws SQLException {
        List<Menu> foodList = menuDAO.getAllFood();
        assertEquals(2, foodList.size());
        assertEquals("Pizza", foodList.get(0).getName());
        assertEquals(2000, foodList.get(0).getPrice());
    }

    @Test
    void getAllDrinks() throws SQLException {
        List<Menu> drinkList = menuDAO.getAllDrinks();
        assertEquals(2, drinkList.size());
        assertEquals("Coca Cola", drinkList.get(0).getName());
        assertEquals(700, drinkList.get(0).getPrice());
    }
}