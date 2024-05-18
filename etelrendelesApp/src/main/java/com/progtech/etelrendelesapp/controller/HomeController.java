package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.database.Database;
import com.progtech.etelrendelesapp.model.MenuItem;
import com.progtech.etelrendelesapp.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    private User currentUser;
    private MenuController menuController;

    public HomeController() {
        this.menuController = new MenuController();
    }

    public HomeController(User currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private Button button_Logout;

    @FXML
    private Button button_balance;

    @FXML
    private Button button_food;

    @FXML
    private Button button_drink;

    @FXML
    private Button button_pay;

    @FXML
    private Label lbl_price;

    @FXML
    private TableView<MenuItem> tView_menu;

    @FXML
    private TableColumn<MenuItem, String> col_name;

    @FXML
    private TableColumn<MenuItem, Integer> col_price;

    @FXML
    private TableView<?> tView_order;

    @FXML
    private Label lbl_balance;


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        loadBalanceFromDatabase();
    }


    @FXML
    public void initialize() {
            col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            col_price.setCellFactory(column -> new TableCell<MenuItem, Integer>() {
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

            showFood();

    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
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
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Nem sikerült betölteni az összeadás nézetet: " + e.getMessage());
        }
    }

    @FXML
    public void handlePay(ActionEvent event) {
        String priceText = lbl_price.getText();
        if (priceText.equals("0") || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Először vegye fel a rendelést");
        } else {
            try {
                double price = Double.parseDouble(priceText);

                // Az insertOrder metódus meghívása itt történik
                // insertOrder(getUserId(), price);

                tView_order.getItems().clear();
                showAlert(Alert.AlertType.INFORMATION, "Sikeres fizetés", "Sikeres megrendelés");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Hiba", "Az ár érvénytelen: " + e.getMessage());
            }
        }
    }
    @FXML
    public void showFood() {
        if (menuController == null){
            showAlert(Alert.AlertType.ERROR, "Hiba", "Nem sikerült a betöltés");
            return;
        }
        ObservableList<MenuItem> foodItem = menuController.getFoodItem();
        tView_menu.setItems(foodItem);
    }

    @FXML
    public void showDrink() {
        if (menuController == null){
            showAlert(Alert.AlertType.ERROR, "Hiba", "Nem sikerült a betöltés");
            return;
        }
        ObservableList<MenuItem> drinkItem = menuController.getDrinkItem();
        tView_menu.setItems(drinkItem);
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void loadBalanceFromDatabase() {
        if (currentUser == null)
            showAlert(Alert.AlertType.ERROR, "Hiba", "Felhasználó nem elérhető");
        String sql = "SELECT balance FROM user WHERE email = ?";
        try (Connection conn = Database.ConnectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, currentUser.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int balance = rs.getInt("balance");
                currentUser.setBalance(balance);
                lbl_balance.setText(String.valueOf(balance));
            }else{
                showAlert(Alert.AlertType.ERROR, "Hiba", "Nem található egyenleg");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }
}
