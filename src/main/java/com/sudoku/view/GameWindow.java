package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWindow extends Stage {
    private GameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/GameWindow.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku");
        this.setResizable(false);
        this.initStyle(javafx.stage.StageStyle.UNDECORATED);
    }

    private static class Holder{
       private static GameWindow INSTANCE = null;
    }
    public static GameWindow getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new GameWindow();
        }
        return Holder.INSTANCE;
    }

    public static void showInstance() throws IOException {
        Holder.INSTANCE.show();
    }
    public static void closeInstance()  {
        Holder.INSTANCE.close();
    }



}
