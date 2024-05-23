package com.progtech.etelrendelesapp.factory;

import com.progtech.etelrendelesapp.model.BasicDrink;
import com.progtech.etelrendelesapp.model.HSQLDBTestDatabase;
import com.progtech.etelrendelesapp.model.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DrinkFactoryTest {
    private Connection connection;
    private DrinkFactory drinkFactory;

    @BeforeEach
    public void setUp() throws SQLException {
        connection = HSQLDBTestDatabase.getConnection();
        HSQLDBTestDatabase.createTable(connection);
        drinkFactory = new DrinkFactory();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HSQLDBTestDatabase.dropTable(connection);
        connection.close();
    }

    @Test
    void createMenu() {
        Menu menu = drinkFactory.createMenu("Coca Cola");
        assertNotNull(menu);
        assertTrue(menu instanceof BasicDrink);
        BasicDrink drink = (BasicDrink) menu;
        assertEquals("Coca Cola", drink.getName());
        assertEquals(700, drink.getPrice());
    }
}