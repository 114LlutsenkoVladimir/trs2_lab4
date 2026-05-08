package com.example.trs2lab4.controller;

import javafx.scene.control.Alert;

public interface ShowError {
    default void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
