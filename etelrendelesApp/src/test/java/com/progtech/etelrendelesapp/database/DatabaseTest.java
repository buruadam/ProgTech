package com.progtech.etelrendelesapp.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void connectToDatabase() {
        Connection connection = Database.ConnectToDatabase();

        assertNotNull(connection);

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
