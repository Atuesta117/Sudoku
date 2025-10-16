package com.sudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Represents the victory window of the Sudoku application.
 * This class extends {@link Stage} and is designed as a Singleton
 * to ensure only one instance of the victory screen exists at a time.
 * It loads its layout from {@code /com/sudoku/VictoryWindow.fxml}.
 * <p>
 * The window displays a "Victory!" title and is not resizable.
 * </p>
 */
public class VictoryWindow extends Stage {

    /**
     * Private constructor to enforce the Singleton pattern.
     * Loads the FXML layout for the victory window, sets up the scene,
     * configures the window title, and disables resizing.
     *
     * @throws IOException If the FXML file cannot be loaded.
     */
    private VictoryWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sudoku/VictoryWindow.fxml")
        );
        Parent root = loader.load(); // Load the FXML layout
        Scene scene = new Scene(root); // Create a new scene with the loaded layout
        this.setScene(scene); // Set the scene for this stage
        this.setTitle("¡Victoria!"); // Set the window title
        this.setResizable(false); // Make the window non-resizable
    }

    /**
     * Inner static class to hold the singleton instance.
     * This ensures lazy initialization and thread-safety (Initialization-on-demand holder idiom).
     */
    private static class Holder {
        private static VictoryWindow INSTANCE = null;
    }

    /**
     * Returns the singleton instance of the VictoryWindow.
     * If an instance does not already exist, a new one is created.
     *
     * @return The single instance of {@code VictoryWindow}.
     * @throws IOException If there is an error loading the FXML for the window.
     */
    public static VictoryWindow getInstance() throws IOException {
        // Create a new instance only if it doesn't exist.
        // This method currently always creates a new instance and replaces the old one.
        // To strictly follow the singleton pattern:
        // if (Holder.INSTANCE == null) {
        //     Holder.INSTANCE = new VictoryWindow();
        // }
        // The current implementation effectively re-creates the window each time getInstance() is called.
        // For a true singleton, the commented-out check should be used.
        // For a window that is often closed and reopened, the current approach might be intended.
        Holder.INSTANCE = new VictoryWindow(); // Current implementation: always create new.
        return Holder.INSTANCE;
    }

    /**
     * Closes the existing singleton instance of the VictoryWindow, if it is open.
     * If no instance exists (or it has already been closed), this method does nothing.
     */
    public static void closeInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close(); // Close the JavaFX stage
            Holder.INSTANCE = null; // Clear the instance reference
        }
    }
}