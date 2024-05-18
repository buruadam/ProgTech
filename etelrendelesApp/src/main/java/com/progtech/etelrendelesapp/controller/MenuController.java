package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuController {
    public ObservableList<MenuItem> getFoodItem() {
        return FXCollections.observableArrayList(
                new MenuItem("Pizza", 1500),
                new MenuItem("Hamburger", 1200),
                new MenuItem("Saláta", 900)
        );
    }

    public ObservableList<MenuItem> getDrinkItem() {
        return FXCollections.observableArrayList(
                new MenuItem("Coca Cola", 500),
                new MenuItem("Narancslé", 600),
                new MenuItem("Víz", 300)
        );
    }
}
