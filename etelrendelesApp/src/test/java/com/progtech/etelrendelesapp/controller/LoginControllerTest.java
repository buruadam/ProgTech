package com.progtech.etelrendelesapp.controller;

import com.progtech.etelrendelesapp.model.LoginDAO;
import com.progtech.etelrendelesapp.model.User;
import javafx.application.Platform;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @BeforeAll
    static void setupOnce() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {
            });
        }
    }

    @Test
    void handleLoginTest() {
        LoginDAO mockLoginDAO = Mockito.mock(LoginDAO.class);

        LoginController controller = new LoginController(mockLoginDAO);

        TextField emailField = new TextField("test@example.com");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("correct-password");
        Label messageLabel = new Label();

        controller.setEmailField(emailField);
        controller.setPasswordField(passwordField);
        controller.setMessageLabel(messageLabel);

        when(mockLoginDAO.authenticate(any(User.class))).thenReturn(true);

        controller.handleLogin();

        assertEquals("Helytelen felhasználónév vagy jelszó", messageLabel.getText());
    }
}