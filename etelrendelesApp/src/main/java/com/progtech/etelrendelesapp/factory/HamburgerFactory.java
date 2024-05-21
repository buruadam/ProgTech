package com.progtech.etelrendelesapp.factory;

import com.progtech.etelrendelesapp.database.Database;
import com.progtech.etelrendelesapp.model.BasicFood;
import com.progtech.etelrendelesapp.model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HamburgerFactory extends MenuFactory{
    @Override
    public Menu createMenu(String name){
        String query = "SELECT * FROM food WHERE name = ?";
        try(Connection connection = Database.ConnectToDatabase(); PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new BasicFood(rs.getString("name"), rs.getInt("price"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
