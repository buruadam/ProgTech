package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.progtech.etelrendelesapp.model.LoginDAO;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private LoginDAO loginDAO;

    public LoginController(LoginDAO mockLoginDAO) {
        loginDAO = new LoginDAO();
    }

    public LoginController() { loginDAO = new LoginDAO(); }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = new User(email, password);

        boolean isAuthenticated = loginDAO.authenticate(user);

        if (isAuthenticated) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/home-view.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root);

                Stage currentStage = (Stage) emailField.getScene().getWindow();
                currentStage.setScene(newScene);
                currentStage.setTitle("ÉtelrendelésAPP");
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Helytelen felhasználónév vagy jelszó");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    public void navToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/register-view.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);

            Stage currentStage = (Stage) emailField.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.setTitle("ÉtelrendelésAPP");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
