package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class VictoryWindow extends Stage {

    private VictoryWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sudoku/VictoryWindow.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("¡Victoria!");
        this.setResizable(false);
    }

    private static class Holder {
        private static VictoryWindow INSTANCE = null;
    }

    public static VictoryWindow getInstance() throws IOException {
        Holder.INSTANCE = new VictoryWindow();
        return Holder.INSTANCE;
    }

    public static void closeInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
        }
    }
}