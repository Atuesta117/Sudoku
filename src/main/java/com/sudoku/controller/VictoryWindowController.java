package com.sudoku.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the Victory Window (the screen displayed when the Sudoku
 * puzzle is successfully completed).
 * <p>
 * This class handles user interactions within the victory dialog, primarily
 * managing the exit or close functionality of the application.
 * </p>
 */
public class VictoryWindowController {

    /**
     * The JavaFX Button element used to close the game.
     * Injected automatically by the FXML loader.
     */
    @FXML
    private Button exitButton;

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is automatically called by the FXMLLoader.
     */
    @FXML
    private void initialize() {
        System.out.println("VictoryWindowController initialized successfully");
    }

    /**
     * Handles the action event for closing the game.
     * This method closes the current window (stage) and then terminates the
     * entire Java Virtual Machine (JVM).
     */
    @FXML
    private void closeGame() {
        // Get the current window (Stage) from the exitButton's scene
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // Close the window
        stage.close();

        // Terminate the application completely
        System.exit(0);
    }
}