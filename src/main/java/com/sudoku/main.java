package com.sudoku;

import com.sudoku.model.Board;
import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuMainMenu;

import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws    Exception {
        GameWindow.getInstance().showInstance();
        Float hola = 0.1f;
        Float no = 1.0f;
        System.out.println(hola+no);

        //System.out.println(board.validateInput(1.0f, 1.5f));

        //Here the first window created is the main menu
        SudokuMainMenu.getInstance().show();


    }
}
