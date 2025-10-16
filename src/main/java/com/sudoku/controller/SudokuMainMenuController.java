package com.sudoku.controller;

import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuMainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SudokuMainMenuController {

    @FXML
    private Button playButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button exitButton;


    @FXML
    public void initialize() {
        System.out.println("prueba jeje");
    }

    @FXML
    void handlePlayButton(ActionEvent event) {
        try {
            System.out.println("Iniciando juego...");

            SudokuMainMenu.closeInstance();

            GameWindow.getInstance().show();

        } catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("  ");
        }
    }

    @FXML
    void handleOptionsButton(ActionEvent event) {
        System.out.println("Ventana en proceso de creacion...");
    }

    @FXML
    void handleExitButton(ActionEvent event) {
        //This line close the app entirely
        System.exit(0);
    }

}
