package com.sudoku.model;

import java.util.*;

public class SudokuGenerator {

    private final int SIZE = 6;
    private final int BOX_ROWS = 2;    // cada bloque tiene 2 filas
    private final int BOX_COLS = 3;    // cada bloque tiene 3 columnas
    private final int BLOCK_ROWS = 3;  // 3 filas de bloques
    private final int BLOCK_COLS = 2;  // 2 columnas de bloques
    private int[][] grid = new int[SIZE][SIZE];
    private final Random random = new Random();

    public SudokuGenerator() {
        generate();
    }

    // ----------------------------------------------------------
    // Genera un sudoku completo usando backtracking
    // ----------------------------------------------------------
    private boolean generate() {
        return fillCell(0, 0);
    }

    private boolean fillCell(int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col + 1) % SIZE;

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collections.shuffle(nums, random);

        for (int num : nums) {
            if (isValid(row, col, num)) {
                grid[row][col] = num;
                if (fillCell(nextRow, nextCol)) {
                    return true;
                }
                grid[row][col] = 0;
            }
        }
        return false;
    }

    // ----------------------------------------------------------
    // Validación: revisa fila, columna y bloque 2x3
    // ----------------------------------------------------------
    private boolean isValid(int row, int col, int num) {
        // Fila
        for (int c = 0; c < SIZE; c++) {
            if (grid[row][c] == num) return false;
        }

        // Columna
        for (int r = 0; r < SIZE; r++) {
            if (grid[r][col] == num) return false;
        }

        // Bloque 2x3
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
    // Obtener los números de una sección (1 a 6)
    // Layout de 6 bloques: 3 filas × 2 columnas
    // Sección 1: fila 0, col 0
    // Sección 2: fila 0, col 1
    // Sección 3: fila 1, col 0
    // Sección 4: fila 1, col 1
    // Sección 5: fila 2, col 0
    // Sección 6: fila 2, col 1
    // ----------------------------------------------------------
    public List<Integer> getSection(int sectionNumber) {
        List<Integer> values = new ArrayList<>();

        if (sectionNumber < 1 || sectionNumber > 6) {
            throw new IllegalArgumentException("El número de sección debe estar entre 1 y 6");
        }

        // Convertir número de sección (1-6) a posición de bloque (3 filas × 2 columnas)
        int blockRow = (sectionNumber - 1) / BLOCK_COLS;    // 0, 1 o 2
        int blockCol = (sectionNumber - 1) % BLOCK_COLS;    // 0 o 1

        int startRow = blockRow * BOX_ROWS;        // 0, 2 o 4
        int startCol = blockCol * BOX_COLS;        // 0 o 3

        System.out.println("Sección " + sectionNumber +
                " → blockRow=" + blockRow + ", blockCol=" + blockCol +
                " → startRow=" + startRow + ", startCol=" + startCol);

        // Recorrer el bloque correctamente (2 filas × 3 columnas)
        for (int r = startRow; r < startRow + BOX_ROWS && r < SIZE; r++) {
            for (int c = startCol; c < startCol + BOX_COLS && c < SIZE; c++) {
                values.add(grid[r][c]);
            }
        }

        System.out.println("Sección " + sectionNumber + " tiene " + values.size() + " valores: " + values);

        return values;
    }

    // ----------------------------------------------------------
    // Mostrar el sudoku completo
    // ----------------------------------------------------------
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

    // ----------------------------------------------------------
    // Método auxiliar para verificar
    // ----------------------------------------------------------
    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        generator.printSudoku();
        System.out.println("\n--- Leyendo secciones ---");
        for (int i = 1; i <= 6; i++) {
            System.out.println("Sección " + i + ": " + generator.getSection(i));
        }
    }
}