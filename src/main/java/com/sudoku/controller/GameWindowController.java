package com.sudoku.controller;

import com.sudoku.model.Board;
import com.sudoku.model.Helper;
import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuMainMenu;
import com.sudoku.view.VictoryWindow;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Controller class for the Sudoku game window.
 * <p>
 * This class handles all the logic for managing user input, validating Sudoku rules,
 * interacting with the Board model, updating the UI, and managing navigation between
 * different sections of the application.
 * </p>
 */
public class GameWindowController {
    /** It tells us if the player completed the sudoku*/
    private boolean victoryShown = false;


    /** The main Sudoku board model instance. */
    private Board board = new Board();

    /** Helper class used to generate Sudoku hints. */
    private Helper helper = new Helper(board);

    /** GridPane representing the Sudoku layout in the FXML view. */
    @FXML
    private GridPane sudokuGrid;

    /** 2D array for easier access to Sudoku TextFields by row and column. */
    private TextField[][] textFields = new TextField[6][6];

    /** Queue storing the last six entered values by the player. */
    private Queue<String> lastValues = new LinkedList<>();

    /** Label used to display the last values entered by the user. */
    @FXML
    private Label textFieldLastValues;

    /** Button to go back to the main menu. */
    @FXML
    private Button buttonBack;

    /** Button to close the current game window. */
    @FXML
    private Button buttonClose;

    /** Button to trigger the help (hint) feature. */
    @FXML
    private Button help;

    /**
     * Initializes the game window and configures all TextFields inside the Sudoku grid.
     * <p>
     * This method sets input filters, initializes the grid with board values,
     * handles navigation with arrow keys, and listens to text changes to trigger validation.
     * </p>
     */
    @FXML
    private void initialize() {

        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                Integer row = GridPane.getRowIndex(tf);
                Integer col = GridPane.getColumnIndex(tf);
                textFields[row][col] = tf;

                ignoreInvalidInputs(tf);
                setEmptyTextFields(tf);
                setiIrrenewableValues(tf);

                // we listen the changer
                setiIrrenewableValues(tf);

                // Listen for text changes in each field
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    board.setNodeValue(tf.getId(), newVal.isEmpty() ? " " : newVal);

                    if(!newVal.matches("[0-6]")) {
                        tf.setText("");
                    } else {
                        // Validate all
                        validateAllTextFields();
                        addValueToList(newVal);

                        // Verificar victoria - CÓDIGO ACTUALIZADO
                        if (!victoryShown && board.isSudokuCompleteAndValid()) {
                            victoryShown = true;
                            Platform.runLater(() -> {
                                try {
                                    System.out.println("Intentando abrir ventana de victoria...");

                    // Only accept digits 1–6
                    if (!newVal.matches("[1-6]")) {
                        tf.setText("");
                        return;
                    }

                    // Revalidate all cells
                    validateAllTextFields();

                                    // Cargar la ventana de victoria
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/VictoryWindow.fxml"));
                                    Parent root = loader.load();

                                    Stage victoryStage = new Stage();
                                    victoryStage.setScene(new Scene(root));
                                    victoryStage.setTitle("¡Victoria!");
                                    victoryStage.setResizable(false);
                                    victoryStage.show();

                                    System.out.println("Ventana de victoria abierta!");

                                    // Cerrar ventana actual del juego
                                    Stage currentStage = (Stage) sudokuGrid.getScene().getWindow();
                                    currentStage.close();

                                } catch (IOException e) {
                                    System.err.println("ERROR AL ABRIR VENTANA DE VICTORIA:");
                                    e.printStackTrace();
                                }
                            });
                        }
                    }

                });

                tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> handleArrowNavigation(e, row, col));
            }
        }

        // Final validation on startup
        validateAllTextFields();

        if(board.isSudokuCompleteAndValid()){
            System.out.println("ganaste un pene");
        }
    }

    /**
     * Fills TextFields with values from the board. If a node is empty (" "), it sets it as blank.
     *
     * @param tf the TextField to update
     */
    private void setEmptyTextFields(TextField tf) {
        tf.setText(board.getValueNode(tf.getId()).equals(" ") ? "" : board.getValueNode(tf.getId()));
    }

    /**
     * Validates all TextFields in the Sudoku grid, coloring them red if the value violates Sudoku rules.
     * <p>
     * Cells that are empty or valid are restored to a neutral visual style.
     * </p>
     */
    private void validateAllTextFields() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField tf) {
                String value = tf.getText();

                if (value.isEmpty()) {
                    tf.setStyle("-fx-background-color: transparent; -fx-text-fill: #dcdcdc;");
                    continue;
                }

                if (board.validateInput(tf.getId())) {
                    tf.setStyle("-fx-background-color: transparent; -fx-text-fill: #dcdcdc;");
                } else {
                    tf.setStyle("-fx-text-fill: red; -fx-border-width: 2px;");
                }
            }
        }
    }

    /**
     * Makes TextFields representing initial Sudoku values uneditable.
     *
     * @param tf the TextField to configure
     */
    private void setiIrrenewableValues(TextField tf) {
        if (board.getNode(tf.getId()).getIsInitialValue()) {
            tf.setEditable(false);
        }
    }

    /**
     * Restricts TextField input to numbers 1 through 6 only.
     *
     * @param tf the TextField to attach the filter to
     */
    private void ignoreInvalidInputs(TextField tf) {
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[1-6]?")) {
                return change;
            }
            return null;
        }));
    }

    /**
     * Handles arrow key navigation across Sudoku cells.
     *
     * @param e   the KeyEvent triggered by an arrow key
     * @param row the current row of the focused cell
     * @param col the current column of the focused cell
     */
    private void handleArrowNavigation(KeyEvent e, int row, int col) {
        switch (e.getCode()) {
            case RIGHT -> moveFocus(row, col + 1);
            case LEFT -> moveFocus(row, col - 1);
            case DOWN -> moveFocus(row + 1, col);
            case UP -> moveFocus(row - 1, col);
        }
    }

    /**
     * Moves the focus to another cell in the Sudoku grid.
     *
     * @param newRow the target row index
     * @param newCol the target column index
     */
    private void moveFocus(int newRow, int newCol) {
        if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < 6) {
            TextField next = textFields[newRow][newCol];
            if (next != null && next.isEditable()) {
                next.requestFocus();
            } else {
                // Si no es editable, sigue avanzando en esa dirección
                moveFocusRecursively(newRow, newCol, newRow - (next == null ? 0 : 0), newCol);
            }
        }
    }

    /**
     * Recursively skips non-editable cells during navigation.
     *
     * @param row      the current row index
     * @param col      the current column index
     * @param deltaRow the row step
     * @param deltaCol the column step
     */
    private void moveFocusRecursively(int row, int col, int deltaRow, int deltaCol) {
        // Movimiento recursivo para saltar celdas bloqueadas
        if (row < 0 || row >= 6 || col < 0 || col >= 6) return;
        TextField next = textFields[row][col];
        if (next != null && next.isEditable()) {
            next.requestFocus();
        }
    }

    /**
     * Adds a value to the queue of recently entered numbers and updates the display label.
     *
     * @param value the number entered by the user
     */
    private void addValueToList(String value) {
        lastValues.add(value);
        if (lastValues.size() > 6) {
            lastValues.poll();
        }
        showLastValues();
    }

    /**
     * Displays the last entered values on the label at the bottom of the UI.
     */
    void showLastValues() {
        StringBuilder message = new StringBuilder();
        for (String value : lastValues) {
            message.append(value).append(" ");
        }
        textFieldLastValues.setText("Last Numbers Typing: "+ message);
    }

    /**
     * Handles the "Back" button action — closes the game and reopens the main menu.
     *
     * @param event the button click event
     * @throws IOException if the menu cannot be loaded
     */
    @FXML
    void backMenu(ActionEvent event) throws IOException {
        GameWindow.getInstance().closeInstance();
        SudokuMainMenu.getInstance().showInstance();
    }

    /**
     * Handles the "Close" button action — closes the current game window.
     *
     * @param event the button click event
     * @throws IOException if the window cannot be closed
     */
    @FXML
    void closeGame(ActionEvent event) throws IOException {
        GameWindow.getInstance().closeInstance();
    }

    /**
     * Handles the "Help" button action — fills a random empty cell with the correct value.
     * The modified cell is highlighted briefly in gold.
     *
     * @param event the button click event
     */
    @FXML
    void help(ActionEvent event) {
        String updatedId = helper.getValueHelp();
        printAllNodes();
        if (updatedId != null) {
            highlightHintCell(updatedId);
        }
    }

    /**
     * Updates all TextFields with their current board values and applies validation colors.
     */
    private void printAllNodes() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField tf) {
                tf.setText(board.getValueNode(tf.getId()));
                if (board.validateInput(tf.getId())) {
                    tf.setStyle("-fx-background-color: transparent;");
                } else {
                    tf.setStyle("-fx-text-fill: red;");
                }
            }
        }
    }

    /**
     * Highlights a specific TextField temporarily to indicate a provided    hint.
     *
     * @param nodeId the ID of the cell to highlight, the value of the node of this cell was changed
     */
    private void highlightHintCell(String nodeId) {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField tf) {
                if (nodeId.equals(tf.getId())) {
                    tf.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> tf.setStyle(""));
                    pause.play();
                    break;
                }
            }
        }
    }
}
