package com.sudoku;

import com.sudoku.model.Board;
import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuWelcomeStage;

import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws    Exception {
        GameWindow.getInstance().showInstance();




    }
}
