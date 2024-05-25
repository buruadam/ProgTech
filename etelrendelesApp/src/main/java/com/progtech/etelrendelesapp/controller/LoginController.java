package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.logger.AppLogger;
import com.progtech.etelrendelesapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.progtech.etelrendelesapp.model.LoginDAO;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private LoginDAO loginDAO;

    public LoginController() { loginDAO = new LoginDAO(); }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = new User(email, password);

        boolean isAuthenticated = loginDAO.authenticate(user);

        if (isAuthenticated) {
            AppLogger.log(Level.INFO, "Sikeres bejelentkezés: " + email);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/home-view.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root);

                HomeController homeController = loader.getController();
                homeController.setCurrentUser(user);

                Stage currentStage = (Stage) emailField.getScene().getWindow();
                currentStage.setScene(newScene);
                currentStage.setTitle("ÉtelrendelésAPP");
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AppLogger.log(Level.WARNING, "Sikertelen bejelentkezés: " + email);
            messageLabel.setText("Helytelen felhasználónév vagy jelszó");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void navToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/register-view.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root ,600,350);
            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("ÉtelrendelésAPP");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
