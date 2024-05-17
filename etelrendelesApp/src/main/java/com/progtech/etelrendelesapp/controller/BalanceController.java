package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.database.Database;
import com.progtech.etelrendelesapp.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BalanceController {

    @FXML
    private TextField tField_Balance;

    private User currentUser;

    private HomeController homeController;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setHomeController(HomeController homeController){
        this.homeController = homeController;
    }

    @FXML
    public void AddBalanceAction(ActionEvent event) {
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "A felhasználói információ nem érhető el.");
            return;
        }

        try {
            int osszeg = Integer.parseInt(tField_Balance.getText());

            if (osszeg < 0) {
                showAlert(Alert.AlertType.ERROR,"Hiba", "Az összeg nem lehet negatív");
                return;
            }
            if (osszeg < 1500) {
                showAlert(Alert.AlertType.ERROR, "Hiba", "Minimum 1500-at kell feltölteni");
                return;
            }

            int newBalance = currentUser.getBalance() + osszeg;
            updateBalanceInDatabase(newBalance);
            currentUser.setBalance(newBalance);
            if (homeController != null){
                homeController.loadBalanceFromDatabase();
            }

            showAlert(Alert.AlertType.INFORMATION, "Siker", "Egyenleg sikeresen frissítve.");

            Stage stage = (Stage) tField_Balance.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Érvényes számot adjon meg.");
        }
    }

    private void updateBalanceInDatabase(int newBalance) {
        String sql = "UPDATE user SET balance = ? WHERE email = ?";
        try (Connection conn = Database.ConnectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, currentUser.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Hiba", "Adatbázis hiba: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
