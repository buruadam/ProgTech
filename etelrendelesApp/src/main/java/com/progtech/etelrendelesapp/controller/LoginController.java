package com.progtech.etelrendelesapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.progtech.etelrendelesapp.model.Login;
import com.progtech.etelrendelesapp.model.LoginDAO;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private LoginDAO loginDAO;

    public LoginController() {
        loginDAO = new LoginDAO();
    }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Login login = new Login(email, password);

        boolean isAuthenticated = loginDAO.authenticate(login);

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
            messageLabel.setText("Invalid email or password.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
