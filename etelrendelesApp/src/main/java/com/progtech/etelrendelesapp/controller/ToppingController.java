package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class ToppingController {

    @FXML
    private CheckBox checkBox_cheese;

    @FXML
    private CheckBox checkBox_bacon;

    @FXML
    private CheckBox checkBox_meal;

    @FXML
    private CheckBox checkBox_onion;

    @FXML
    private CheckBox checkBox_bbq;

    @FXML
    private RadioButton radioButton_noTopping;

    @FXML
    private Label lbl_totalPrice;

    @FXML
    private Button button_addTopping;

    private int toppingPrice = 200;
    private int totalPrice;

    private Menu menu;

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.totalPrice = menu.getPrice();
        lbl_totalPrice.setText(totalPrice + " Ft");
    }
    public void initialize() {
        radioButton_noTopping.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                disableToppings();
            }else{
                enableToppings();
            }
            updatePrice();
        });

        checkBox_cheese.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_bacon.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_meal.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_onion.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_bbq.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());

    }
    private void disableToppings(){
        checkBox_cheese.setSelected(false);
        checkBox_bacon.setSelected(false);
        checkBox_meal.setSelected(false);
        checkBox_onion.setSelected(false);
        checkBox_bbq.setSelected(false);

        checkBox_cheese.setDisable(true);
        checkBox_bacon.setDisable(true);
        checkBox_meal.setDisable(true);
        checkBox_onion.setDisable(true);
        checkBox_bbq.setDisable(true);
    }

    private void enableToppings(){
        checkBox_cheese.setDisable(false);
        checkBox_bacon.setDisable(false);
        checkBox_meal.setDisable(false);
        checkBox_onion.setDisable(false);
        checkBox_bbq.setDisable(false);
    }

    private void updatePrice(){
        totalPrice = menu.getPrice();

        if (checkBox_cheese.isSelected()) totalPrice += toppingPrice;
        if (checkBox_bacon.isSelected()) totalPrice += toppingPrice;
        if (checkBox_meal.isSelected()) totalPrice += toppingPrice;
        if (checkBox_onion.isSelected()) totalPrice += toppingPrice;
        if (checkBox_bbq.isSelected()) totalPrice += toppingPrice;
        lbl_totalPrice.setText(totalPrice + " Ft");
    }

    @FXML
    public void addToppings(){
        if (!radioButton_noTopping.isSelected()) {
            if (checkBox_cheese.isSelected()) menu.addTopping("Sajt");
            if (checkBox_bacon.isSelected()) menu.addTopping("Bacon");
            if (checkBox_meal.isSelected()) menu.addTopping("Hús");
            if (checkBox_onion.isSelected()) menu.addTopping("Hagyma");
            if (checkBox_bbq.isSelected()) menu.addTopping("BBQ");
        }
        menu.setPrice(totalPrice);
        Stage stage = (Stage) lbl_totalPrice.getScene().getWindow();
        stage.close();
    }
}
