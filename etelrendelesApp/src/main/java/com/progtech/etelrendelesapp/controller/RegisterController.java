package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.RegisterDAO;
import com.progtech.etelrendelesapp.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField last_nameField;

    @FXML
    private TextField first_nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private RegisterDAO registerDAO;

    public RegisterController() { registerDAO = new RegisterDAO(); }

    public void handleRegister() {
        String lastName = last_nameField.getText();
        String firstName = first_nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = new User(lastName, firstName, email, password);

        try {
            registerDAO.registerUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/login-view.fxml"));
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

    public void navToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/progtech/etelrendelesapp/view/login-view.fxml"));
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
