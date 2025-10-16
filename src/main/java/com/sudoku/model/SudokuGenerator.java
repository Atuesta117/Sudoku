package com.sudoku.model;

import java.util.*;

/**
 * Important clarification: This class was created with AI (the only one generated entirely by AI).
 * The main idea is to create a solved sudoku and be able to obtain the numbers of the sections.
 * Generates a fully solved 6x6 Sudoku board using a backtracking algorithm.
 * <p>
 * Each Sudoku follows the 6×6 grid layout with 6 blocks (2×3 each):
 * <ul>
 *     <li>Block layout: 3 rows × 2 columns of blocks.</li>
 *     <li>Each block contains 2 rows and 3 columns of cells.</li>
 * </ul>
 * The generator can also return the numbers from a specific block (1–6).
 * </p>
 *
 * <p><strong>Block numbering layout:</strong></p>
 * <pre>
 * +---------+---------+
 * | Block 1 | Block 2 |
 * +---------+---------+
 * | Block 3 | Block 4 |
 * +---------+---------+
 * | Block 5 | Block 6 |
 * +---------+---------+
 * </pre>
 *
 * Example usage:
 * <pre>
 *     SudokuGenerator generator = new SudokuGenerator();
 *     generator.printSudoku();
 *     List&lt;Integer&gt; section1 = generator.getSection(1);
 * </pre>
 */
public class SudokuGenerator {

    /** Total grid size (6x6 Sudoku). */
    private final int SIZE = 6;

    /** Number of rows in each block (2 rows per block). */
    private final int BOX_ROWS = 2;

    /** Number of columns in each block (3 columns per block). */
    private final int BOX_COLS = 3;

    /** Number of block rows (3 total). */
    private final int BLOCK_ROWS = 3;

    /** Number of block columns (2 total). */
    private final int BLOCK_COLS = 2;

    /** 6x6 Sudoku grid. */
    private int[][] grid = new int[SIZE][SIZE];

    /** Random number generator used for shuffling numbers. */
    private final Random random = new Random();

    /**
     * Constructs a new SudokuGenerator and immediately generates
     * a valid completed Sudoku grid.
     */
    public SudokuGenerator() {
        generate();
    }

    // ----------------------------------------------------------
    // Grid generation
    // ----------------------------------------------------------

    /**
     * Generates a complete Sudoku grid using recursive backtracking.
     *
     * @return {@code true} if the Sudoku was successfully generated
     */
    private boolean generate() {
        return fillCell(0, 0);
    }

    /**
     * Recursively fills each cell of the Sudoku grid.
     *
     * @param row the current row index
     * @param col the current column index
     * @return {@code true} if the grid is successfully filled
     */
    private boolean fillCell(int row, int col) {
        if (row == SIZE) return true; // Base case: entire grid filled

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collections.shuffle(nums, random); // Randomize order

        for (int num : nums) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                if (fillCell(nextRow, nextCol)) {
                    return true;
                }
                grid[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    // ----------------------------------------------------------
    // Validation logic
    // ----------------------------------------------------------

    /**
     * Checks whether placing a number in a given cell is valid
     * according to Sudoku rules (unique in row, column, and block).
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param num the number to test
     * @return {@code true} if the number placement is valid
     */
    private boolean isValid(int row, int col, int num) {
        // Row check
        for (int c = 0; c < SIZE; c++) {
            if (grid[row][c] == num) return false;
        }

        // Column check
        for (int r = 0; r < SIZE; r++) {
            if (grid[r][col] == num) return false;
        }

        // Block check (2×3)
        int startRow = (row / BOX_ROWS) * BOX_ROWS;
        int startCol = (col / BOX_COLS) * BOX_COLS;
        for (int r = startRow; r < startRow + BOX_ROWS; r++) {
            for (int c = startCol; c < startCol + BOX_COLS; c++) {
                if (grid[r][c] == num) return false;
            }
        }

        return true;
    }

    // ----------------------------------------------------------
    // Section retrieval
    // ----------------------------------------------------------

    /**
     * Returns the list of numbers contained in one of the six 2×3 Sudoku blocks.
     * <p>
     * The Sudoku has 3 rows × 2 columns of blocks.
     * </p>
     *
     * <p>Mapping of block numbers:</p>
     * <ul>
     *   <li>Section 1 → (0,0)</li>
     *   <li>Section 2 → (0,1)</li>
     *   <li>Section 3 → (1,0)</li>
     *   <li>Section 4 → (1,1)</li>
     *   <li>Section 5 → (2,0)</li>
     *   <li>Section 6 → (2,1)</li>
     * </ul>
     *
     * @param sectionNumber the section index (1–6)
     * @return list of integers (the six values in that section)
     * @throws IllegalArgumentException if {@code sectionNumber} is not between 1 and 6
     */
    public List<Integer> getSection(int sectionNumber) {
        List<Integer> values = new ArrayList<>();

        if (sectionNumber < 1 || sectionNumber > 6) {
            throw new IllegalArgumentException("Section number must be between 1 and 6");
        }

        int blockRow = (sectionNumber - 1) / BLOCK_COLS; // 0, 1, 2
        int blockCol = (sectionNumber - 1) % BLOCK_COLS; // 0 or 1

        int startRow = blockRow * BOX_ROWS; // 0, 2, 4
        int startCol = blockCol * BOX_COLS; // 0, 3


        for (int r = startRow; r < startRow + BOX_ROWS && r < SIZE; r++) {
            for (int c = startCol; c < startCol + BOX_COLS && c < SIZE; c++) {
                values.add(grid[r][c]);
            }
        }


        return values;
    }

    // ----------------------------------------------------------
    // Debugging / visualization
    // ----------------------------------------------------------

    /**
     * Prints the complete Sudoku grid in a formatted way.
     * Useful for debugging or visualization in console output.
     */
    public void printSudoku() {
        for (int i = 0; i < SIZE; i++) {
            if (i % BOX_ROWS == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            for (int j = 0; j < SIZE; j++) {
                if (j % BOX_COLS == 0 && j != 0) {
                    System.out.print(" | ");
                }
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }


}
