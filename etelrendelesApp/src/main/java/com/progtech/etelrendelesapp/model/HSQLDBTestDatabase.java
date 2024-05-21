package com.progtech.etelrendelesapp.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDBTestDatabase {
    private static final String JDBC_URL = "jdbc:hsqldb:mem:testdb";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    public static void createTable(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS user (" +
                    "id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "first_name VARCHAR(255), " +
                    "last_name VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255))");

            String hashedPassword = BCrypt.hashpw("validpassword", BCrypt.gensalt());
            stmt.execute("INSERT INTO user (first_name, last_name, email, password) " +
                    "VALUES ('Test', 'User', 'test@test.com', '" + hashedPassword + "')");
        }
    }



    public static void dropTable(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE user");
        }
    }
}
