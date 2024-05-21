package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ToppingController {

    @FXML
    private CheckBox checkBox_toppingOne;

    @FXML
    private CheckBox checkBox_toppingTwo;

    @FXML
    private CheckBox checkBox_toppingThree;

    @FXML
    private CheckBox checkBox_toppingFour;

    @FXML
    private CheckBox checkBox_toppingFive;

    @FXML
    private RadioButton radioButton_noTopping;

    @FXML
    private Label lbl_totalPrice;

    @FXML
    private Button button_addTopping;

    private int toppingPrice = 200;
    private int totalPrice;

    private Menu menu;

    private Map<String, CheckBox> pizzaToppings;
    private Map<String, CheckBox> hamburgerToppings;

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.totalPrice = menu.getPrice();
        lbl_totalPrice.setText(totalPrice + " Ft");

        if (menu.getName().toLowerCase().contains("pizza")){
            checkBox_toppingOne.setText("Sonka");
            checkBox_toppingTwo.setText("Bacon");
            checkBox_toppingThree.setText("Kukorica");
            checkBox_toppingFour.setText("Sajt");
            checkBox_toppingFive.setText("Pepperoni");
        } else if (menu.getName().toLowerCase().contains("hamburger")){
            checkBox_toppingOne.setText("Bacon");
            checkBox_toppingTwo.setText("Grillezett csirkemell");
            checkBox_toppingThree.setText("Vöröshagyma");
            checkBox_toppingFour.setText("BBQ");
            checkBox_toppingFive.setText("Paprika");
        }
    }
    public void initialize() {
        pizzaToppings = new HashMap<>();
        pizzaToppings.put("Sonka", checkBox_toppingOne);
        pizzaToppings.put("Bacon", checkBox_toppingTwo);
        pizzaToppings.put("Kukorica", checkBox_toppingThree);
        pizzaToppings.put("Sajt", checkBox_toppingFour);
        pizzaToppings.put("Pepperoni", checkBox_toppingFive);


        hamburgerToppings = new HashMap<>();
        hamburgerToppings.put("Bacon",checkBox_toppingOne);
        hamburgerToppings.put("Grillezett csirkemell", checkBox_toppingTwo);
        hamburgerToppings.put("Vöröshagyma", checkBox_toppingThree);
        hamburgerToppings.put("BBQ", checkBox_toppingFour);
        hamburgerToppings.put("Paprika", checkBox_toppingFive);

        radioButton_noTopping.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                disableToppings();
            }else{
                enableToppings();
            }
            updatePrice();
        });

        checkBox_toppingOne.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_toppingTwo.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_toppingThree.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_toppingFour.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());
        checkBox_toppingFive.selectedProperty().addListener((observable, oldValue, newValue) -> updatePrice());

    }
    private void disableToppings(){
        checkBox_toppingOne.setSelected(false);
        checkBox_toppingTwo.setSelected(false);
        checkBox_toppingThree.setSelected(false);
        checkBox_toppingFour.setSelected(false);
        checkBox_toppingFive.setSelected(false);

        checkBox_toppingOne.setDisable(true);
        checkBox_toppingTwo.setDisable(true);
        checkBox_toppingThree.setDisable(true);
        checkBox_toppingFour.setDisable(true);
        checkBox_toppingFive.setDisable(true);
    }

    private void enableToppings(){
        checkBox_toppingOne.setDisable(false);
        checkBox_toppingTwo.setDisable(false);
        checkBox_toppingThree.setDisable(false);
        checkBox_toppingFour.setDisable(false);
        checkBox_toppingFive.setDisable(false);
    }

    private void updatePrice(){
        totalPrice = menu.getPrice();

        if (checkBox_toppingOne.isSelected()) totalPrice += toppingPrice;
        if (checkBox_toppingTwo.isSelected()) totalPrice += toppingPrice;
        if (checkBox_toppingThree.isSelected()) totalPrice += toppingPrice;
        if (checkBox_toppingFour.isSelected()) totalPrice += toppingPrice;
        if (checkBox_toppingFive.isSelected()) totalPrice += toppingPrice;
        lbl_totalPrice.setText(totalPrice + " Ft");
    }

    @FXML
    public void addToppings(){
        if (!radioButton_noTopping.isSelected()) {
            Map<String, CheckBox> thisToppings;
            if (menu.getName().toLowerCase().contains("pizza")){
                thisToppings = pizzaToppings;
            }else if(menu.getName().toLowerCase().contains("hamburger"))
                thisToppings = hamburgerToppings;
            else
                return;

            for (Map.Entry<String, CheckBox> entry : thisToppings.entrySet()){
                if(entry.getValue().isSelected())
                    menu.addTopping(entry.getKey());
            }
        }
        menu.setPrice(totalPrice);
        Stage stage = (Stage) lbl_totalPrice.getScene().getWindow();
        stage.close();
    }
}
