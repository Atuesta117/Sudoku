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
        Board board = new Board();
        board.initializeSections();
        board.setNodeValue(1.0f,1.2f, "1");
        board.setNodeValue(1.0f,1.5f, "2");
        board.setNodeValue(3.0f,3.2f, "6");
        board.setNodeValue(1.0f,1.3f, "3");
        board.setNodeValue(1.0f,1.1f, "4");
        board.setNodeValue(2.0f,2.3f, "5");

        //System.out.println(board.validateInput(1.0f, 1.5f));

        //Here the first window created is the main menu
        SudokuMainMenu.getInstance().show();


    }
}
