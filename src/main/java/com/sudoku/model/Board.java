package com.sudoku.model;

import java.util.*;

/**
 * Represents the logical structure of a 6×6 Sudoku board.
 * <p>
 * This class builds the Sudoku board as a tree-like structure:
 * the root node contains 6 section nodes (blocks),
 * and each section node contains 6 cell nodes.
 * Each node is represented by a {@link Node} object with a unique float ID.
 * </p>
 *
 * <p>Example structure:</p>
 * <pre>
 * Root (0.0)
 * ├── Section 1 (1.0)
 * │   ├── Cell 1 (1.1)
 * │   ├── Cell 2 (1.2)
 * │   └── ...
 * ├── Section 2 (2.0)
 * │   ├── Cell 1 (2.1)
 * │   └── ...
 * </pre>
 *
 * <p>
 * The board automatically generates a valid Sudoku solution using
 * {@link SudokuGenerator}, and then randomly fills two initial values
 * per section as clues.
 * </p>
 */
public class Board implements IBoard {

    /** Root node representing the entire Sudoku board. */
    private Node root;
    /** The Sudoku generator used to create solved puzzles. */
    private final SudokuGenerator generator = new SudokuGenerator();

    /**
     * Returns the Sudoku generator.
     * @return The {@link SudokuGenerator} instance.
     */
    @Override
    public SudokuGenerator getGenerator() {return this.generator;}

    /**
     * Returns the root node of the Sudoku board.
     * @return The root {@link Node}.
     */
    Node getroot() { return this.root; }

    /**
     * Constructs a Sudoku board.
     * <ul>
     * <li>Creates an empty hierarchical structure (sections and cells).</li>
     * <li>Fills two random valid values per section as initial clues.</li>
     * </ul>
     */
    public Board() {
        root = new Node(0.0f);
        root.setValor(""); // Set initial value for the root (not used in actual game logic)
        initializeStructure();
        fillInitialValues();
    }

    /** IDs representing each Sudoku section (1–6). */
    private  List<Float> idSectionsBoard = Arrays.asList(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);

    /**
     * IDs representing each cell inside a section (0.1–0.6).
     * The idea is that for a child ID like 1.2, its father is 1.0, and the child's fractional part is 0.2.
     */
    List<Float> idCellsBoard = Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f);

    // ----------------------------------------------------------
    // Initialization
    // ----------------------------------------------------------

    /**
     * Builds the logical structure of the Sudoku board:
     * 6 section nodes, each containing 6 empty cell nodes.
     */
    private void initializeStructure() {
        // Clear existing children to ensure a fresh structure
        root.getChildren().clear();

        for (Float idSection : idSectionsBoard) {
            Node sectionNode = new Node(idSection);
            root.addCHildren(sectionNode); // Add section to root

            for (Float boxId : idCellsBoard) {
                // Create cell node with ID combining section ID and cell's fractional ID
                Node cellNode = new Node(idSection + boxId);
                sectionNode.addCHildren(cellNode); // Add cell to its section
            }
        }
    }

    /**
     * Fills the board with initial values based on a valid Sudoku solution.
     * <ul>
     * <li>Uses {@link SudokuGenerator} to create a solved Sudoku.</li>
     * <li>Randomly selects two cells in each section to show as clues.</li>
     * <li>Marks those cells as initial (fixed) values that cannot be changed by the user.</li>
     * </ul>
     */
    private void fillInitialValues() {
        // The generator already has a complete Sudoku solution

        for (int i = 0; i < root.getChildren().size(); i++) {
            Node section = root.getChildren().get(i);
            // Get the solved values for the current section (1-indexed)
            List<Integer> sectionValues = generator.getSection(i + 1);
            List<Node> children = section.getChildren();

            // Create a list of indices (0 to 5) and shuffle them to pick random cells
            List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4, 5);
            Collections.shuffle(indices);
            // Choose the first two random indices
            List<Integer> chosenIndices = indices.subList(0, 2);

            for (int idx : chosenIndices) {
                Node child = children.get(idx);
                int solvedValue = sectionValues.get(idx);
                // Set the initial value on the board
                setNodeValue(section.getId(), child.getId(), String.valueOf(solvedValue));
                // Mark this cell as an initial clue
                child.setIsInitialValue(true);
            }
        }
    }

    // ----------------------------------------------------------
    // ID Parsing
    // ----------------------------------------------------------

    /**
     * Extracts the parent (section) ID from a text field identifier.
     * Assumes a format like "tSC" where S is the section number and C is the cell number.
     * <p>Example: "t13" → section ID = 1.0f</p>
     *
     * @param textFieldId the ID of the text field (e.g., "t13")
     * @return the section ID as a float
     */
    private Float getfatherId(String textFieldId) {
        String sudokuSection = textFieldId.charAt(1) + ".0";
        return Float.parseFloat(sudokuSection);
    }

    /**
     * Extracts the child (cell) ID from a text field identifier.
     * Assumes a format like "tSC" where S is the section number and C is the cell number.
     * <p>Example: "t13" → cell ID = 1.3f</p>
     *
     * @param textFieldId the ID of the text field (e.g., "t13")
     * @return the cell ID as a float
     */
    private Float getchildId(String textFieldId) {
        // The format is assumed to be like "tSC", e.g., "t13" for Section 1, Cell 3.
        // The first char is 't', second is section number, third is cell number.
        // For example, charAt(1) gives '1', charAt(2) gives '3'.
        // So, "1.3" is constructed and parsed.
        // Note: Original code used charAt(3), which might be "P1C3" format.
        // Assuming "tSC" format as per common GUI element naming.
        String idChildren = textFieldId.charAt(1) + "." + textFieldId.charAt(2);
        return Float.parseFloat(idChildren);
    }

    // ----------------------------------------------------------
    // Node Value Handling
    // ----------------------------------------------------------

    /**
     * Sets the value of a cell node based on the corresponding TextField ID.
     * This method is part of the {@link IBoard} interface.
     *
     * @param idTextfield the TextField ID (e.g., "t13")
     * @param value the new value to assign
     */
    @Override
    public void setNodeValue(String idTextfield, String value) {
        Float fatherId = getfatherId(idTextfield);
        Float childId = getchildId(idTextfield);
        for (Node nodeFather : root.getChildren()) {
            if (nodeFather.getId().equals(fatherId)) {
                for (Node nodeChild : nodeFather.getChildren()) {
                    if (nodeChild.getId().equals(childId)) {
                        nodeChild.setValor(value);
                        return; // Value set, exit loops
                    }
                }
            }
        }
    }

    /**
     * Sets the value of a cell node directly by section and child ID.
     * This is a protected helper method used internally, for example, during initialization.
     *
     * @param fatherId   the section ID
     * @param childId the cell ID
     * @param value      the value to assign
     */
    protected void setNodeValue(Float fatherId, Float childId, String value) {
        for (Node nodeFather : root.getChildren()) {
            if (nodeFather.getId().equals(fatherId)) {
                for (Node nodeChild : nodeFather.getChildren()) {
                    if (nodeChild.getId().equals(childId)) {
                        nodeChild.setValor(value);
                        return; // Value set, exit loops
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------
    // Validation
    // ----------------------------------------------------------

    /**
     * Validates an input value according to Sudoku rules:
     * <ul>
     * <li>No repetition in the same section (block).</li>
     * <li>No repetition in the same column.</li>
     * <li>No repetition in the same row.</li>
     * </ul>
     * This is a protected helper method for internal validation.
     *
     * @param fatherId   the section ID
     * @param childId the cell ID
     * @param value      the value to validate
     * @return {@code true} if the value is valid, otherwise {@code false}
     */
    protected boolean validateInput(Float fatherId, Float childId, String value) {
        return validateSection(fatherId, value)
                && validateColumn(fatherId, childId, value)
                && validateRow(fatherId, childId, value);
    }

    /**
     * Validates the value of a cell using its TextField ID.
     * This method is part of the {@link IBoard} interface.
     * <ul>
     * <li>No repetition in the same section (block).</li>
     * <li>No repetition in the same column.</li>
     * <li>No repetition in the same row.</li>
     * </ul>
     * @param textFieldId the TextField ID (e.g., "t13")
     * @return {@code true} if the value does not violate Sudoku rules
     */
    @Override
    public boolean validateInput(String textFieldId) {
        String childValue = getValueNode(textFieldId);
        // If the cell is empty, it's considered valid for the purpose of not violating rules.
        // It's checked for completeness later by isSudokuCompleteAndValid.
        if (childValue.trim().isEmpty() || childValue.equals(" ")) {
            return true;
        }

        Float childId = getchildId(textFieldId);
        Float fatherId = getfatherId(textFieldId);
        return validateSection(fatherId, childValue)
                && validateColumn(fatherId, childId, childValue)
                && validateRow(fatherId, childId, childValue);
    }

    /**
     * Validates that a section (2×3 block) does not contain repeated values.
     *
     * @param fatherId   the section ID
     * @param childValue the value to validate against existing values in the section.
     * @return {@code true} if the value is not repeated within the section, or if it's the only instance.
     */
    private boolean validateSection(Float fatherId, String childValue) {
        List<String> sectionValues = new ArrayList<>();
        for (Node sectionNode : root.getChildren()) {
            if (sectionNode.getId().equals(fatherId)) {
                for (Node cellNode : sectionNode.getChildren()) {
                    // Only add non-empty values to the list for checking
                    if (!cellNode.getValue().equals(" ")) {
                        sectionValues.add(cellNode.getValue());
                    }
                }
                break; // Found the section, no need to check other root children
            }
        }
        // Count occurrences of the value; should be 0 or 1.
        long count = sectionValues.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }

    /**
     * Validates that a value is not repeated within the same Sudoku column.
     *
     * @param fatherId   the section ID of the cell being validated.
     * @param childId the cell ID of the cell being validated.
     * @param childValue the value to validate against existing values in the column.
     * @return {@code true} if the column is valid (value not repeated or is the only instance).
     */
    private boolean validateColumn(Float fatherId, Float childId, String childValue) {
        // Determine which set of sections form the current column.
        // The Sudoku is 6x6, divided into 6 sections (blocks).
        // Each column spans across two sections.
        // E.g., Columns 1,2,3 are in sections 1,3,5 (first half)
        // E.g., Columns 4,5,6 are in sections 2,4,6 (second half)
        int initialSectionIndex = (Math.round(fatherId) % 2 == 0) ? 1 : 0; // 0 for odd sections (1,3,5), 1 for even sections (2,4,6)

        // This constant helps determine the cell's "relative column position" within its section.
        // Example: If childId - fatherId is 0.1, 0.2, 0.3 (first row of cells in section),
        // or 0.4, 0.5, 0.6 (second row of cells in section).
        // For 6x6 Sudoku, cells 0.1, 0.4 are in column 1; 0.2, 0.5 in column 2; 0.3, 0.6 in column 3 (relative to the section's 3 columns)
        // The 'columnConstantPerSection' helps differentiate between the first 3 cells and the last 3 cells in a section for column mapping.
        Float columnConstantPerSection = 0.3f; // Used to shift the child's fractional ID for logical column mapping

        List<Node> allSections = root.getChildren();
        List<Node> sectionsInColumnGroup = new ArrayList<>(); // Sections that share the same "vertical slice"
        List<String> valuesInColumn = new ArrayList<>();

        // Identify the "column identifier" based on the child's fractional ID within its parent.
        // This maps the 0.1-0.6 range to 0.1-0.3 for column validation logic.
        Float columnIdentifier = (childId - fatherId < 0.4f)
                ? (childId - fatherId)
                : (childId - fatherId - columnConstantPerSection);

        // Collect sections that belong to the same "column strip" across the board.
        // E.g., for fatherId=1.0, initialSectionIndex is 0. Loop i=0,2,4 -> sections 1,3,5
        // E.g., for fatherId=2.0, initialSectionIndex is 1. Loop i=1,3,5 -> sections 2,4,6
        for (int i = initialSectionIndex; i < 6; i += 2) {
            sectionsInColumnGroup.add(allSections.get(i));
        }

        // Iterate through all sections that potentially share this column
        for (Node sectionNode : sectionsInColumnGroup) {
            for (Node cellNode : sectionNode.getChildren()) {
                final float EPSILON = 0.0001f; // For floating-point comparisons
                float relativeCellId = cellNode.getId() - sectionNode.getId(); // Fractional part of cell ID

                // Check if this cell belongs to the same logical column as the cell being validated.
                // It accounts for cells in the "first row" (0.1, 0.2, 0.3) and "second row" (0.4, 0.5, 0.6)
                // within a 2x3 section, mapping them to the correct logical column.
                if (Math.abs(relativeCellId - columnIdentifier) < EPSILON ||
                        Math.abs(relativeCellId - (columnIdentifier + columnConstantPerSection)) < EPSILON) {
                    if (!cellNode.getValue().equals(" ")) {
                        valuesInColumn.add(cellNode.getValue());
                    }
                }
            }
        }
        // Count occurrences of the value; should be 0 or 1.
        long count = valuesInColumn.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }


    /**
     * Validates that a value is not repeated within the same Sudoku row.
     *
     * @param fatherId   the section ID of the cell being validated.
     * @param childId the cell ID of the cell being validated.
     * @param childValue the value to validate against existing values in the row.
     * @return {@code true} if the cell with respect to the row is valid (value not repeated or is the only instance).
     */
    private boolean validateRow(Float fatherId, Float childId, String childValue) {
        // Determine which fractional cell IDs within a section correspond to the same row.
        // For a 6x6 Sudoku with 2x3 sections:
        // Cells 0.1, 0.2, 0.3 are in the "top" row of a section.
        // Cells 0.4, 0.5, 0.6 are in the "bottom" row of a section.
        List<Float> rowConstants = (childId - fatherId < 0.4f)
                ? Arrays.asList(0.1f, 0.2f, 0.3f) // Top half of a section
                : Arrays.asList(0.4f, 0.5f, 0.6f); // Bottom half of a section

        List<String> valuesInRow = new ArrayList<>();

        // Sudoku rows span across two adjacent sections (e.g., Section 1 and Section 2 form rows 1-3).
        // Find the "neighboring" section that shares the same rows.
        Float neighborId = (Math.round(fatherId) % 2 == 0) ? fatherId - 1.0f : fatherId + 1.0f;

        // Get the current section node and its neighbor section node.
        // Math.round(fatherId) - 1 converts 1.0f to index 0, 2.0f to index 1, etc.
        Node currentSectionNode = root.getChildren().get(Math.round(fatherId) - 1);
        Node neighborSectionNode = root.getChildren().get(Math.round(neighborId) - 1);
        final float EPSILON = 0.0001f; // For floating-point comparisons

        // Iterate through cells in the current section
        for (Node cellNode : currentSectionNode.getChildren()) {
            float relativeCellId = cellNode.getId() - currentSectionNode.getId(); // Fractional part of cell ID
            // Check if the cell's relative ID matches one of the row constants
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - relativeCellId) < EPSILON);
            if (matches && !cellNode.getValue().trim().isEmpty()) {
                valuesInRow.add(cellNode.getValue().trim());
            }
        }
        // Iterate through cells in the neighboring section
        for (Node cellNode : neighborSectionNode.getChildren()) {
            float relativeCellId = cellNode.getId() - neighborSectionNode.getId(); // Fractional part of cell ID
            // Check if the cell's relative ID matches one of the row constants
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - relativeCellId) < EPSILON);
            if (matches && !cellNode.getValue().trim().isEmpty()) {
                valuesInRow.add(cellNode.getValue().trim());
            }
        }

        // Count occurrences of the value; should be 0 or 1.
        long count = valuesInRow.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }

    // ----------------------------------------------------------
    // Node Access
    // ----------------------------------------------------------

    /**
     * Returns the string value of a node by its TextField ID.
     * This method is part of the {@link IBoard} interface.
     *
     * @param textFieldId the TextField ID (e.g., "P1C3")
     * @return the value stored in that node, or an empty string if not found.
     */
    @Override
    public String getValueNode(String textFieldId) {
        Float childId = getchildId(textFieldId);
        Float fatherId = getfatherId(textFieldId);
        // Find the specific cell node and return its value
        Node targetNode = getNode(fatherId, childId); // Reusing the private helper getNode
        return (targetNode != null) ? targetNode.getValue() : "";
    }

    /**
     * Returns the root node.
     * This method is protected, intended for internal use or subclasses within the model package.
     * @return the root {@link Node}.
     */
    protected Node getRoot() {
        return root;
    }

    /**
     * Finds a specific node by its parent (section) and child (cell) ID.
     * This is a private helper method for internal node retrieval.
     *
     * @param fatherId   the section ID (e.g., 1.0f)
     * @param childId the cell ID (e.g., 1.3f)
     * @return the matching {@link Node} or {@code null} if not found
     */
    private Node getNode(Float fatherId, Float childId) {
        for (Node section : root.getChildren()) {
            if (section.getId().equals(fatherId)) {
                for (Node cell : section.getChildren()) {
                    if (cell.getId().equals(childId)) {
                        return cell;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds a specific node using a TextField ID.
     * This method is part of the {@link IBoard} interface.
     *
     * @param textFieldId the TextField ID (e.g., "P1C2")
     * @return the corresponding {@link Node} or {@code null} if not found
     */
    @Override
    public Node getNode(String textFieldId) {
        Float childId = getchildId(textFieldId);
        Float fatherId = getfatherId(textFieldId);
        // Reuse the private helper getNode method
        return getNode(fatherId, childId);
    }

    /**
     * Checks if the Sudoku board is completely filled and all values are valid.
     * This method is part of the {@link IBoard} interface.
     *
     * @return true if all 36 cells are filled with valid values; false otherwise.
     */
    @Override
    public boolean isSudokuCompleteAndValid() {
        // No need for validCount, as we return false immediately on any invalid or empty cell.

        for (Node section : root.getChildren()) {
            if (section.getId().equals(fatherId)) {
                for (Node cell : section.getChildren()) {
                    if (cell.getId().equals(childId)) {
                        return cell;
                    }
                }
            }
        }

        // If the loop finishes, it means all cells are filled and valid.
        return true;
    }

    /**
     * Checks if the Sudoku board is completely filled and all values are valid.
     *
     * @return true if all 36 cells are filled with valid values; false otherwise.
     */
    public boolean isSudokuCompleteAndValid() {
        int validCount = 0;

        for (Node section : root.getChildren()) {
            for (Node cell : section.getChildren()) {
                String value = cell.getValue();

                // ignorar vacíos
                if (value.equals("") || value.trim().isEmpty()) {
                    return false; // si hay alguna vacía, ya no está completo
                }

                Float fatherId = section.getId();
                Float childId = cell.getId();

                // si alguna es inválida, el sudoku no está correctamente completado
                if (!validateInput(fatherId, childId, value)) {
                    return false;
                }

                validCount++;
            }
        }

        return validCount == 36;
    }

}