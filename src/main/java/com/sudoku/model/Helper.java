package com.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides hint-related functionality for the Sudoku game.
 * <p>
 * The {@code Helper} class interacts with the {@link Board} and {@link SudokuGenerator}
 * to locate an empty (non-initial) cell within the board, assign it the correct value,
 * and return its identifier in the format {@code P#C#}, where:
 * </p>
 * <ul>
 *   <li>{@code P#} = Parent section index (1–6)</li>
 *   <li>{@code C#} = Child cell index within the section (1–6)</li>
 * </ul>
 * This is typically used to implement the “Help” or “Hint” feature in the game UI.
 * </p>
 */
public class Helper {

    /** Reference to the current Sudoku board. */
    private Board board;

    /** Sudoku generator responsible for producing valid Sudoku values. */
    private SudokuGenerator generator;

    /**
     * Constructs a {@code Helper} associated with a specific {@link Board}.
     *
     * @param board the {@link Board} instance to operate on
     */
    public Helper(Board board) {
        this.board = board;
        this.generator = board.getGenerator();
    }

    /**
     * Provides a hint by filling one random empty (non-initial) cell
     * with its correct Sudoku value.
     * <p>
     * The method randomly selects a section and a cell, retrieves the correct
     * value from the {@link SudokuGenerator}, and updates the corresponding
     * {@link Node} in the board. It then returns the node’s ID in the format
     * {@code P#C#}, which matches the {@code TextField} ID in the UI.
     * </p>
     *
     * @return the ID of the cell that was updated (e.g. {@code "P2C3"}),
     *         or {@code null} if no suitable cell was found
     */
    public String getValueHelp() {
        Node root = board.getRoot();
        List<Node> sections = new ArrayList<>(root.getChildren());
        Collections.shuffle(sections); // randomize section selection

        for (Node section : sections) {
            List<Node> cells = new ArrayList<>(section.getChildren());
            Collections.shuffle(cells); // randomize cell selection

            for (Node cell : cells) {
                if (!cell.getIsInitialValue()) {
                    int sectionIndex = Math.round(section.getId()); // example: 1.0 → 1
                    int cellIndex = section.getChildren().indexOf(cell) + 1;

                    // Get the correct values for this section from the generator
                    List<Integer> valuesSection = generator.getSection(sectionIndex);
                    cell.setValor(valuesSection.get(cellIndex - 1).toString());

                    // Build the node ID (e.g. P3C2)
                    String id = "P" + sectionIndex + "C" + cellIndex;



                    return id; // ID matches the TextField in the UI
                }
            }
        }

        System.out.println("⚠ No available cells for hint.");
        return null;
    }
}
