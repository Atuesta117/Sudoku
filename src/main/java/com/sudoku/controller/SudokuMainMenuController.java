package com.sudoku.controller;

import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuMainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controller class for the main menu of the Sudoku game.
 * <p>
 * This class handles user interactions in the main menu, including starting
 * a new game, opening the options menu, and exiting the application.
 * </p>
 */
public class SudokuMainMenuController {

    /** Button that starts a new Sudoku game. */
    @FXML
    private Button playButton;

    /** Button that opens the options/settings menu (currently in development). */
    @FXML
    private Button optionsButton;

    /** Button that exits the application. */
    @FXML
    private Button exitButton;

    /**
     * Initializes the main menu controller.
     * <p>
     * This method is automatically called by the JavaFX framework after
     * the FXML file has been loaded. It can be used to perform setup
     * operations before the user interacts with the interface.
     * </p>
     */
    @FXML
    public void initialize() {
        System.out.println("Main menu initialized successfully.");
    }

    /**
     * Handles the "Play" button action.
     * <p>
     * This method closes the current main menu window and opens
     * the game window to start a new Sudoku session.
     * </p>
     *
     * @param event the event triggered by clicking the "Play" button
     */
    @FXML
    void handlePlayButton(ActionEvent event) {
        try {
            System.out.println("Starting new game...");

            SudokuMainMenu.closeInstance();
            GameWindow.getInstance().show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while starting the game window.");
        }
    }

    /**
     * Handles the "Options" button action.
     * <p>
     * Currently, this feature is under development and displays a
     * placeholder message in the console.
     * </p>
     *
     * @param event the event triggered by clicking the "Options" button
     */
    @FXML
    void handleOptionsButton(ActionEvent event) {
        System.out.println("Options window is under development...");
    }

    /**
     * Handles the "Exit" button action.
     * <p>
     * This method terminates the entire application immediately.
     * </p>
     *
     * @param event the event triggered by clicking the "Exit" button
     */
    @FXML
    void handleExitButton(ActionEvent event) {
        //This line close the app entirely
        System.exit(0);
    }

}
