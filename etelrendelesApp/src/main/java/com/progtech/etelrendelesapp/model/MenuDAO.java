package com.progtech.etelrendelesapp.model;

import com.progtech.etelrendelesapp.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection connection;

    public MenuDAO() {
        this.connection = Database.ConnectToDatabase();
    }

    public List<Menu> getAllFood() {
        List<Menu> foodList = new ArrayList<>();
        String query = "SELECT * FROM food";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                foodList.add(new BasicFood(rs.getString("name"), rs.getInt("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    public List<Menu> getAllDrinks() {
        List<Menu> drinkList = new ArrayList<>();
        String query = "SELECT * FROM drink";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                drinkList.add(new BasicFood(rs.getString("name"), rs.getInt("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinkList;
    }
}
