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
                    "id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "first_name VARCHAR(255), " +
                    "last_name VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE, " +
                    "password VARCHAR(255), " +
                    "balance INTEGER DEFAULT 0)");

            stmt.execute("CREATE TABLE IF NOT EXISTS food (" +
                    "id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "price INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS drink (" +
                    "id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "price INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                    "id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "user_email VARCHAR(255), " +
                    "total_price DECIMAL(10, 2))");

            stmt.execute("INSERT INTO food (name, price) VALUES ('Pizza', 2000), ('Hamburger', 1800)");

            stmt.execute("INSERT INTO drink (name, price) VALUES ('Coca Cola', 700), ('Fanta', 600)");

            String hashedPassword = BCrypt.hashpw("validpassword", BCrypt.gensalt());
            stmt.execute("INSERT INTO user (first_name, last_name, email, password, balance) " +
                    "VALUES ('Test', 'User', 'test@test.com', '" + hashedPassword + "', 100)");
        }
    }

    public static void dropTable(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE user");
            stmt.execute("DROP TABLE food");
            stmt.execute("DROP TABLE drink");
            stmt.execute("DROP TABLE orders");
        }
    }
}
