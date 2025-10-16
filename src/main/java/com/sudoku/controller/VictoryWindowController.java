package com.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VictoryWindowController {

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        System.out.println("VictoryWindowController inicializado correctamente");
    }

    @FXML
    private void closeGame() {
        System.out.println("Cerrando juego desde VictoryWindow...");
        // Cerrar la ventana actual de victoria
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        // Cerrar toda la aplicación
        System.exit(0);
    }
}