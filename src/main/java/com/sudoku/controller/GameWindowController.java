package com.sudoku.controller;

import com.sudoku.model.Board;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.w3c.dom.ls.LSOutput;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GameWindowController {
    Board board = new Board();

    @FXML
    GridPane sudokuGrid;

    @FXML
    private void initialize() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;
                ignoreInvalidInputs(tf);
                setEmptyTextFields(tf);
                setirrenewableValues(tf);
                // we listen the changer
                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    board.setNodeValue(tf.getId(), newVal.isEmpty() ? " " : newVal);

                    // Validate all
                    validateAllTextFields();
                });
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
}
