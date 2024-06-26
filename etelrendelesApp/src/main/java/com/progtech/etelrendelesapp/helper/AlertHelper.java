package com.progtech.etelrendelesapp.helper;

import javafx.scene.control.Alert;

public class AlertHelper {
    public static void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}