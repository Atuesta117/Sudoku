package com.sudoku.controller;

import com.sudoku.model.Board;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.w3c.dom.ls.LSOutput;

public class GameWindowController {
    Board board = new Board();
    @FXML
    GridPane sudokuGrid;
    @FXML
    private void initialize() {
        // Recorremos todos los nodos dentro del grid
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                TextField tf = (TextField) node;

                tf.textProperty().addListener((obs, oldVal, newVal) -> {
                    System.out.println(tf.getId() + " cambió: " + newVal);
                    if (!newVal.isEmpty()) {board.setNodeValue(tf.getId(), newVal);
                        System.out.println("holaaaaaaaa");

                        if (!board.validateInput(tf.getId())) {
                            tf.setStyle("-fx-border-color: red ; -fx-border-width: 2px;");
                        }
                        else {tf.setStyle("-fx-background-color: transparent");}
                    }
                    else{
                        tf.setStyle("");
                    }

                });

                }
        }
    }


}
