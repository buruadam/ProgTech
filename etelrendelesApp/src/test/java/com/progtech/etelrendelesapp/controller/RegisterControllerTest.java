package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.RegisterDAO;
import com.progtech.etelrendelesapp.model.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterControllerTest {
    @Test
    void handleRegisterTest() {
        RegisterDAO mockRegisterDAO = Mockito.mock(RegisterDAO.class);
        RegisterController controller = new RegisterController(mockRegisterDAO);

        TextField lastNameTextField = new TextField("");
        TextField firstNameTextField = new TextField("Teszt");
        TextField emailTextField = new TextField("test@test.com");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("password123");
        Label messageLabel = new Label();

        controller.setLast_nameField(lastNameTextField);
        controller.setFirst_nameField(firstNameTextField);
        controller.setEmailField(emailTextField);
        controller.setPasswordField(passwordField);
        controller.setMessageLabel(messageLabel);

        controller.handleRegister();

        verify(mockRegisterDAO, never()).registerUser(any(User.class));

        assertEquals("Hiba a regisztráció során!", messageLabel.getText());
    }
}