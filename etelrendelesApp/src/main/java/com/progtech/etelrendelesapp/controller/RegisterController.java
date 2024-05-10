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
    public void setLast_nameField(TextField last_nameField) {
        this.last_nameField = last_nameField;
    }

    public void setFirst_nameField(TextField first_nameField) {
        this.first_nameField = first_nameField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    @FXML
    private TextField last_nameField;

    @FXML
    private TextField first_nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    @FXML
    private Label messageLabel;

    private RegisterDAO registerDAO;

    public RegisterController(RegisterDAO mockRegisterDAO) {
        registerDAO = new RegisterDAO();
    }


    public RegisterController() { registerDAO = new RegisterDAO(); }

    public void handleRegister() {
        String lastName = last_nameField.getText();
        String firstName = first_nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = new User(lastName, firstName, email, password);

        if (!lastName.isEmpty() && !firstName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
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
        else {
            messageLabel.setText("Hiba a regisztráció során!");
            messageLabel.setStyle("-fx-text-fill: red;");
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
