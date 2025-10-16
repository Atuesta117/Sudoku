package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents the main game window for the Sudoku application.
 * <p>
 * This class extends {@link Stage} and is responsible for loading the game interface
 * defined in the {@code GameWindow.fxml} file. It uses the Singleton design pattern to ensure
 * that only one instance of the game window exists at any time.
 * </p>
 */
public class GameWindow extends Stage {

    /**
     * Private constructor that initializes and configures the game window.
     * Loads the FXML file, sets up the scene, defines window properties, and
     * removes the default window decorations for a custom UI.
     * @throws IOException if the FXML resource cannot be loaded
     */
    private GameWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/GameWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Sudoku");
        this.setResizable(false);
        this.initStyle(javafx.stage.StageStyle.UNDECORATED);
    }

    /**
     * Static inner class implementing the Singleton holder pattern.
     * <p>
     * Ensures that the instance is lazily loaded and thread-safe without explicit synchronization.
     * </p>
     */
    private static class Holder {
        /** Singleton instance of the {@link GameWindow}. */
        private static GameWindow INSTANCE = null;
    }

    /**
     * Returns the single instance of {@link GameWindow}, creating it if necessary.
     *
     * @return the singleton {@link GameWindow} instance
     * @throws IOException if the FXML file cannot be loaded during initialization
     */
    public static GameWindow getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new GameWindow();
        }
        return Holder.INSTANCE;
    }

    /**
     * Displays the current instance of the {@link GameWindow}.
     * <p>
     * If the window has not been initialized, it must first be created via {@link #getInstance()}.
     * </p>
     *
     * @throws IOException if the instance has not been properly initialized
     */
    public static void showInstance() throws IOException {
        Holder.INSTANCE.show();
    }

    /**
     * Closes the current instance of the {@link GameWindow}, if it exists.
     * <p>
     * This method does not destroy the instance — it simply hides the window.
     * </p>
     */
    public static void closeInstance() {
        Holder.INSTANCE.close();
    }
}

