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

class PizzaFactoryTest {
    private Connection connection;
    private PizzaFactory pizzaFactory;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        pizzaFactory = new PizzaFactory();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    void createMenu() {
        Menu menu = pizzaFactory.createMenu("Pizza");
        assertNotNull(menu);
        assertTrue(menu instanceof BasicFood);
        BasicFood food = (BasicFood) menu;
        assertEquals("Pizza", food.getName());
        assertEquals(2000, food.getPrice());
    }
}