package com.sudoku.controller;

import com.sudoku.model.Board;
import com.sudoku.model.Helper;
import com.sudoku.view.GameWindow;
import com.sudoku.view.SudokuMainMenu;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class GameWindowController {
    Board board = new Board();
    private Helper helper = new Helper(board);

    @FXML
    GridPane sudokuGrid;

    private TextField[][] textFields = new TextField[6][6];
    private Queue<String> lastValues = new LinkedList<>();

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
                setirrenewableValues(tf);
                // we listen the changer
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    board.setNodeValue(tf.getId(), newVal.isEmpty() ? " " : newVal);
                    if(!newVal.matches("[0-6]")) {
                        tf.setText(""); }
                    else{// Validate all
                        validateAllTextFields();
                        addValueToList(newVal);}

                });

                tf.addEventFilter(KeyEvent.KEY_PRESSED, e -> handleArrowNavigation(e, row, col));
            }
        }

        // Validate all just in case
        validateAllTextFields();
    }
    private void setEmptyTextFields(TextField tf) {
        tf.setText(board.getValueNode(tf.getId()).equals(" ") ? "" : board.getValueNode(tf.getId()));
    }
    private void validateAllTextFields() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                String value = tf.getText();

                if (value.isEmpty()) {
                    tf.setStyle("");
                    continue;
                }

                if (board.validateInput(tf.getId())) {
                    tf.setStyle("-fx-background-color: transparent;");
                } else {
                    tf.setStyle("-fx-text-fill: red; -fx-border-width: 2px;");
                }
            }
        }
    }


    private void setirrenewableValues(TextField tf) {
        if(board.getNode(tf.getId()).getIsInitialValue()){
            tf.setEditable(false);
        }
    }

    private void ignoreInvalidInputs(TextField tf) {
        tf.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            // ignore other thins different to 1 to 6
            if (!character.matches("[1-6]") || character.isEmpty()) {
                event.consume(); //prevents the key from being processed

            }
        });

    }

    private void handleArrowNavigation(KeyEvent e, int row, int col) {
        switch (e.getCode()) {
            case RIGHT -> moveFocus(row, col + 1);
            case LEFT -> moveFocus(row, col - 1);
            case DOWN -> moveFocus(row + 1, col);
            case UP -> moveFocus(row - 1, col);
        }
    }

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

    private void moveFocusRecursively(int row, int col, int deltaRow, int deltaCol) {
        // Movimiento recursivo para saltar celdas bloqueadas
        if (row < 0 || row >= 6 || col < 0 || col >= 6) return;
        TextField next = textFields[row][col];
        if (next != null && next.isEditable()) {
            next.requestFocus();
        }
    }


    private void addValueToList(String value) {

        lastValues.add(value);
        if(lastValues.size() > 6 ){
            lastValues.poll();
        }
        showLastValues();

    }
    @FXML
    private Label textFieldLastValues;


    void showLastValues(){
        StringBuilder message = new StringBuilder();
        for(String value : lastValues){
            message.append(value).append(" ");
        }
        textFieldLastValues.setText(message.toString());
    }

    @FXML
    private Button buttonBack;
    @FXML
    void backMenu(ActionEvent event) throws IOException {
        GameWindow.getInstance().close();
        SudokuMainMenu.getInstance().show();
    }

    @FXML
    private Button buttonClose;

    @FXML
    void closeGame(ActionEvent event) throws IOException {
        GameWindow.getInstance().close();
    }

    @FXML
    private Button help;

    @FXML
    void help(ActionEvent event) {
        String updatedId = helper.getValueHelp(); // devuelve el id del nodo modificado

        printAllNodes(); // refresca los valores en pantalla

        if (updatedId != null) {
            highlightHintCell(updatedId); // resalta ese textfield
        }
    }



    private void printAllNodes(){
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                tf.setText(board.getValueNode(tf.getId()));
            }
        }
    }
    private void highlightHintCell(String nodeId) {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField tf) {
                if (nodeId.equals(tf.getId())) {
                    tf.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");

                    // (Opcional) quitar color después de 2 segundos
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> tf.setStyle(""));
                    pause.play();

                    break;
                }
            }
        }
    }

}
