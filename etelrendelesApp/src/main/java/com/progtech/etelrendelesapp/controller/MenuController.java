package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuController {
    public ObservableList<Menu> getFoodItem() {
        return FXCollections.observableArrayList(
                /*
                new Menu("Pizza", 1500),
                new Menu("Hamburger", 1200),
                new Menu("Saláta", 900)

                 */
        );
    }

    public ObservableList<Menu> getDrinkItem() {
        return FXCollections.observableArrayList(
                /*
                new Menu("Coca Cola", 500),
                new Menu("Narancslé", 600),
                new Menu("Víz", 300)

                 */
        );
    }
}
