package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class SudokuMainMenu extends Stage {
    private SudokuMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sudoku/SudokuMainMenu.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Main Menu");
        this.setResizable(false);
        this.initStyle(javafx.stage.StageStyle.UNDECORATED);
    }

    private static class Holder {
        private static SudokuMainMenu INSTANCE = null;
    }

    public static SudokuMainMenu getInstance() throws IOException {
        Holder.INSTANCE = Holder.INSTANCE != null ?
                Holder.INSTANCE : new SudokuMainMenu();
        return Holder.INSTANCE;
    }

    public static void closeInstance() {
        Holder.INSTANCE.close();
    }

    public static void showInstance() throws IOException {
        Holder.INSTANCE.show();
    }
}


