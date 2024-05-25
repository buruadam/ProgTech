package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.factory.MenuFactory;
import com.progtech.etelrendelesapp.factory.MenuFactorySelector;
import com.progtech.etelrendelesapp.helper.AlertHelper;
import com.progtech.etelrendelesapp.logger.AppLogger;
import com.progtech.etelrendelesapp.model.*;
import com.progtech.etelrendelesapp.model.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class HomeController {

    private User currentUser;
    private HomeDAO homeDAO = new HomeDAO();
    private boolean isFoodSelected = true;

    @FXML
    private Label lbl_price;

    @FXML
    private TableView<Menu> tView_menu;

    @FXML
    private TableColumn<Menu, String> col_name;

    @FXML
    private TableColumn<Menu, Integer> col_price;

    @FXML
    private TableView<Menu> tView_order;

    @FXML
    private TableColumn<Menu, String> orderColName;

    @FXML
    private TableColumn<Menu, Integer> orderColPrice;

    @FXML
    private Label lbl_balance;

    private ObservableList<Menu> orderList = FXCollections.observableArrayList();

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        loadBalanceFromDatabase();
    }


    @FXML
    public void initialize() {
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_price.setCellFactory(column -> new TableCell<Menu, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " Ft");
                }
            }
        });

        orderColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        orderColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderColPrice.setCellFactory(column -> new TableCell<Menu, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " Ft");
                }
            }
        });

        tView_order.setItems(orderList);

        showFood();
    }


    @FXML
    public void handleLogout(ActionEvent event) {
        AppLogger.log(Level.INFO, "Kijelentkezett: " + currentUser.getEmail());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AddBalance(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/balance-view.fxml"));
            Parent root = loader.load();

            BalanceController balanceController = loader.getController();
            balanceController.setCurrentUser(currentUser);
            balanceController.setHomeController(this);

            Stage stage = new Stage();
            stage.setTitle("Add Balance");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Nem sikerült betölteni az összeadás nézetet: " + e.getMessage());
        }
    }

    @FXML
    public void handlePay(ActionEvent event) {
        String priceText = lbl_price.getText().replace(" Ft", "");
        if (priceText.equals("0") || priceText.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Először vegye fel a rendelést");
        } else {
            try {
                double price = Double.parseDouble(priceText);

                if (currentUser.getBalance() < price) {
                    AlertHelper.showAlert(Alert.AlertType.ERROR,"Hiba", "Nincs elég pénzed");
                    return;
                }

                int payedBalance = currentUser.getBalance() - (int) price;
                updateBalanceInDatabase(payedBalance);
                currentUser.setBalance(payedBalance);
                lbl_balance.setText(payedBalance + " Ft");

                insertOrder(currentUser.getEmail(), price);
                AppLogger.log(Level.INFO, "Rendelés leadva: " + currentUser.getEmail() + ", " + price + " Ft");

                tView_order.getItems().clear();
                lbl_price.setText("0 Ft");
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Sikeres fizetés", "Sikeres megrendelés");
            } catch (NumberFormatException e) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Az ár érvénytelen: " + e.getMessage());
            }
        }

    }
    private void updateBalanceInDatabase(int newBalance){
        try {
            homeDAO.updateBalanceInDatabase(currentUser.getEmail(),newBalance);
            lbl_balance.setText(newBalance + " Ft");
        } catch (SQLException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }
    private void insertOrder(String email, double price) {
        try{
            homeDAO.insertOrder(email,price);
        } catch (SQLException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }

    @FXML
    public void showFood() {
        isFoodSelected = true;
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> foodList = menuDAO.getAllFood();
        tView_menu.getItems().setAll(foodList);
    }

    @FXML
    public void showDrink() {
        isFoodSelected = false;
        MenuDAO menuDAO = new MenuDAO();
        List<Menu> drinkList = menuDAO.getAllDrinks();
        tView_menu.getItems().setAll(drinkList);
    }

    public void loadBalanceFromDatabase() {
        if (currentUser == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Felhasználó nem elérhető");
            return;
        }
        try {
            Integer balance = homeDAO.loadBalanceFromDatabase(currentUser.getEmail());
            if (balance != null) {
                currentUser.setBalance(balance);
                lbl_balance.setText(balance + " Ft");
            }else{
                AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Nem található egyenleg");
            }
        } catch (SQLException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }

    @FXML
    public void addToOrder() {
        Menu selectedItem = tView_menu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                int originalPrice = selectedItem.getPrice();

                MenuFactory factory;
                if (isFoodSelected){
                    String foodType = selectedItem.getName().toLowerCase();
                    if (foodType.contains("pizza"))
                        factory = MenuFactorySelector.getFactory("pizza");
                    else if (foodType.contains("hamburger"))
                        factory = MenuFactorySelector.getFactory("hamburger");
                    else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR,"Hiba", "Ismeretlen étel típus");
                        return;
                    }
                }
                else
                    factory = MenuFactorySelector.getFactory("drink");

                Menu selectedItemCopy = factory.createMenu(selectedItem.getName());

                if (isFoodSelected){
                    openToppingWindow(selectedItemCopy);
                }

                orderList.add(selectedItemCopy);
                AppLogger.log(Level.INFO, "Rendeléshez hozzáadva: " + selectedItemCopy.getName() + ", " + selectedItemCopy.getPrice() + " Ft");
                tView_menu.getSelectionModel().clearSelection();
                updateTotalPrice();
                selectedItem.setPrice(originalPrice);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, "Figyelem", "Válasszon ki egy terméket a hozzáadáshoz");
        }
    }
    private void openToppingWindow(Menu menu) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/topping-view.fxml"));
        Parent root = loader.load();
        ToppingController toppingController = loader.getController();
        toppingController.setMenu(menu);

        Stage stage = new Stage();
        stage.setTitle("Válassz feltétet");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    public void RemoveFromOrder() {
        Menu selectedItem = tView_order.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            orderList.remove(selectedItem);
            AppLogger.log(Level.INFO, "Eltávolítva a rendelésről: " + selectedItem.getName() + ", " + selectedItem.getPrice() + " Ft");
            tView_order.getSelectionModel().clearSelection();
            updateTotalPrice();
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, "Figyelem", "Válasszon ki egy terméket az eltávolításhoz");
        }
    }

    private void updateTotalPrice() {
        int totalPrice = orderList.stream().mapToInt(Menu::getPrice).sum();
        lbl_price.setText(totalPrice + " Ft");
    }
}
