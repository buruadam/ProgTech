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
import java.sql.SQLException;

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

    public RegisterController(RegisterDAO mockRegisterDAO)
    {
        registerDAO =  new RegisterDAO();
    }


    public RegisterController() {
        registerDAO = new RegisterDAO();
    }

    public void handleRegister() {
        String lastName = last_nameField.getText();
        String firstName = first_nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!lastName.isEmpty() && !firstName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if(isEmailValid(email) && isPasswordValid(password)){
                try {
                    if (!registerDAO.emailExists(email)){
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
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        messageLabel.setText("Az email már létezik!");
                        messageLabel.setStyle("-fx-text-fill: red;");

                    }
                }catch (SQLException e){
                    e.printStackTrace();
                    messageLabel.setText("Hiba a regisztráció során!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
            else{
                if(!isEmailValid(email)){
                    messageLabel.setText("Az email cím nem megfelelő!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }else {
                    messageLabel.setText("A jelszó nem megfelelő!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                }
            }
        } else{
            messageLabel.setText("Minden mezőt ki kell tölteni!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
    private boolean isPasswordValid(String password){
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        return true;
    }
    private boolean isEmailValid(String email){
        return email.contains("@");
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
