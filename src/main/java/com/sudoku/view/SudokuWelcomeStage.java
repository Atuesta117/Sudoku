package com.sudoku.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SudokuWelcomeStage extends Stage {
    private SudokuWelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sudoku/SudokuWelcomeStage.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("StarWindow");
        this.setResizable(false);
        this.initStyle(javafx.stage.StageStyle.UNDECORATED);
    }

    private static class Holder {
        private static SudokuWelcomeStage INSTANCE = null;
    }

    public static SudokuWelcomeStage getInstance() throws IOException {
        Holder.INSTANCE = Holder.INSTANCE != null ?
                Holder.INSTANCE : new SudokuWelcomeStage();
        return Holder.INSTANCE;
    }

    public static void closeInstance() {
        Holder.INSTANCE.close();
    }

    public static void showInstance() throws IOException {
        Holder.INSTANCE.show();
    }
}


