package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.database.Database;
import com.progtech.etelrendelesapp.model.*;
import com.progtech.etelrendelesapp.model.Menu;
import javafx.collections.FXCollections;
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
import java.util.List;

public class HomeController {

    private User currentUser;
    private MenuController menuController;
    private boolean isFoodSelected = true;

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
        String priceText = lbl_price.getText().replace(" Ft", "");
        if (priceText.equals("0") || priceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Először vegye fel a rendelést");
        } else {
            try {
                double price = Double.parseDouble(priceText);

                if (currentUser.getBalance() < price) {
                    showAlert(Alert.AlertType.ERROR,"Hiba", "Nincs elég pénzed");
                    return;
                }

                int payedBalance = currentUser.getBalance() - (int) price;
                updateBalanceInDatabase(payedBalance);
                currentUser.setBalance(payedBalance);
                lbl_balance.setText(payedBalance + " Ft");

                insertOrder(currentUser.getEmail(), price);

                tView_order.getItems().clear();
                lbl_price.setText("0 Ft");
                showAlert(Alert.AlertType.INFORMATION, "Sikeres fizetés", "Sikeres megrendelés");
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Hiba", "Az ár érvénytelen: " + e.getMessage());
            }
        }

    }
    private void updateBalanceInDatabase(int newBalance){
        String sql = "UPDATE user SET balance = ? WHERE email = ?";
        try (Connection connection = Database.ConnectToDatabase();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, newBalance);
                pstmt.setString(2, currentUser.getEmail());
                pstmt.executeUpdate();
                lbl_balance.setText(newBalance + " Ft");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }
    private void insertOrder(String email, double price) {
        String sql = "INSERT INTO orders (user_email, total_price) VALUES(?, ?)";
        try (Connection connection = Database.ConnectToDatabase();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
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
                lbl_balance.setText(balance + " Ft");
            }else{
                showAlert(Alert.AlertType.ERROR, "Hiba", "Nem található egyenleg");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }

    @FXML
    public void addToOrder() {
        Menu selectedFood = tView_menu.getSelectionModel().getSelectedItem();
        if (selectedFood != null) {
            try {
                int originalPrice = selectedFood.getPrice();
                BasicFood selectedFoodCopy = new BasicFood(selectedFood.getName(), selectedFood.getPrice());
                if (isFoodSelected){

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/topping-view.fxml"));
                    Parent root = loader.load();
                    ToppingController toppingController = loader.getController();
                    toppingController.setMenu(selectedFoodCopy);

                    Stage stage = new Stage();
                    stage.setTitle("Válassz feltétet");
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                }

                orderList.add(selectedFoodCopy);
                tView_menu.getSelectionModel().clearSelection();
                updateTotalPrice();
                selectedFood.setPrice(originalPrice);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Figyelem", "Válasszon ki egy terméket a hozzáadáshoz");
        }
    }
    public boolean isFood(String name) {
        return !name.toLowerCase().contains("drink");
    }

    @FXML
    public void RemoveFromOrder() {
        Menu selectedFood = tView_order.getSelectionModel().getSelectedItem();
        if (selectedFood != null) {
            orderList.remove(selectedFood);
            tView_order.getSelectionModel().clearSelection();
            updateTotalPrice();
        } else {
            showAlert(Alert.AlertType.WARNING, "Figyelem", "Válasszon ki egy terméket az eltávolításhoz");
        }
    }

    private void updateTotalPrice() {
        int totalPrice = orderList.stream().mapToInt(Menu::getPrice).sum();
        lbl_price.setText(totalPrice + " Ft");
    }
}
