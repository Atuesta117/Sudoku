package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents the main menu window of the Sudoku application.
 * <p>
 * This class extends {@link Stage} and manages the main menu interface defined in
 * the {@code SudokuMainMenu.fxml} file. It uses the Singleton design pattern to ensure
 * only one instance of the main menu exists throughout the application's lifecycle.
 * </p>
 */
public class SudokuMainMenu extends Stage {

    /**
     * Private constructor that initializes and configures the main menu window.
     * <p>
     * Loads the FXML layout, sets up the scene, defines window properties,
     * and removes default decorations for a clean custom design.
     * </p>
     *
     * @throws IOException if the FXML resource cannot be loaded
     */
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

    /**
     * Static inner class implementing the Singleton holder pattern.
     * <p>
     * Ensures lazy and thread-safe initialization of the {@link SudokuMainMenu} instance
     * without requiring explicit synchronization.
     * </p>
     */
    private static class Holder {
        /** Singleton instance of the {@link SudokuMainMenu}. */
        private static SudokuMainMenu INSTANCE = null;
    }

    /**
     * Returns the single instance of {@link SudokuMainMenu}, creating it if necessary.
     *
     * @return the singleton {@link SudokuMainMenu} instance
     * @throws IOException if the FXML file cannot be loaded during initialization
     */
    public static SudokuMainMenu getInstance() throws IOException {
        Holder.INSTANCE = (Holder.INSTANCE != null)
                ? Holder.INSTANCE
                : new SudokuMainMenu();
        return Holder.INSTANCE;
    }

    /**
     * Closes the current instance of the {@link SudokuMainMenu}, if it exists.
     * <p>
     * This does not destroy the instance — it only hides the window from view.
     * </p>
     */
    public static void closeInstance() {
        Holder.INSTANCE.close();
    }

    /**
     * Displays the current instance of the {@link SudokuMainMenu}.
     * <p>
     * If the window has not been initialized yet, it must first be created
     * via {@link #getInstance()} before calling this method.
     * </p>
     *
     * @throws IOException if the instance has not been properly initialized
     */
    public static void showInstance() throws IOException {
        Holder.INSTANCE.show();
    }
}
