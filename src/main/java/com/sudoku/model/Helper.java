package com.sudoku.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Helper {
    private Board board;
    private SudokuGenerator generator;
    public Helper( Board board) {
        this.board = board;
        this.generator = board.getGenerator();
    }

    public String getValueHelp() {
        Node root = board.getRoot();
        List<Node> sections = new ArrayList<>(root.getChildren());
        Collections.shuffle(sections);

        for (Node section : sections) {
            List<Node> cells = new ArrayList<>(section.getChildren());
            Collections.shuffle(cells);

            for (Node cell : cells) {
                if (!cell.getIsInitialValue()) {
                    int sectionIndex = Math.round(section.getId()); // por ejemplo 1.0 → 1
                    int cellIndex = section.getChildren().indexOf(cell) + 1; // índice del hijo dentro del padre

                    // obtiene el valor correcto desde el generador
                    List<Integer> valuesSection = generator.getSection(sectionIndex);
                    cell.setValor(valuesSection.get(cellIndex - 1).toString());

                    // 🔹 devuelve el id con el formato correcto
                    String id = "P" + sectionIndex + "C" + cellIndex;

                    System.out.println("Hint → " + id + " = " + valuesSection.get(cellIndex - 1));

                    return id; // ✅ este id coincide con el del TextField
                }
            }
        }

        System.out.println("⚠ No available cells for hint.");
        return null;
    }






}

