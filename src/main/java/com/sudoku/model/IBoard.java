package com.sudoku.model;
/**
 * Defines the core functionality for interacting with the logic
 * of a 6x6 Sudoku board.
 * This interface abstracts the internal board structure (like the node hierarchy)
 * and exposes only the necessary methods for the view (GUI) or controller.
 */
public interface IBoard {

        /**
         * Sets the value of a cell on the board.
         * The cell is identified using a GUI text field ID, which is mapped
         * to the cell's coordinates.
         *
         * @param textFieldId The ID of the text field (e.g., "P1C3", implying Section 1, Cell 3).
         * @param value The new value (as a String) to assign to the cell.
         */
        void setNodeValue(String textFieldId, String value);

        /**
         * Validates if the current value contained in a cell complies with
         * Sudoku rules (no repetition in row, column, or 2x3 block).
         * The cell is identified using a text field ID.
         *
         * @param textFieldId The ID of the text field (e.g., "P1C3").
         * @return {@code true} if the value is valid according to Sudoku rules;
         * {@code false} otherwise.
         */
        boolean validateInput(String textFieldId);

        /**
         * Returns the current string value of a specific cell on the board.
         *
         * @param textFieldId The ID of the text field (e.g., "P1C3").
         * @return The value (as a String) stored in that cell.
         */
        String getValueNode(String textFieldId);

        /**
         * Finds the corresponding cell for the text field ID and returns its Node object.
         * Note: This method exposes the internal {@link Node} structure and might
         * be re-evaluated if strict abstraction is a priority.
         *
         * @param textFieldId The ID of the text field (e.g., "P1C2").
         * @return The corresponding {@link Node} for the cell or {@code null} if not found.
         */
        Node getNode(String textFieldId);

        /**
         * Checks if the Sudoku board is completely filled and if all values entered are valid.
         *
         * @return {@code true} if all 36 cells are filled with valid values that
         * do not violate any Sudoku rule; {@code false} otherwise.
         */
        boolean isSudokuCompleteAndValid();

        /**
         * Returns the Sudoku generator associated with this board.
         *
         * @return The {@link SudokuGenerator} object used to create the solution.
         */
        SudokuGenerator getGenerator();

}
