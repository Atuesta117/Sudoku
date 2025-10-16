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
public class Board {

    /** Root node representing the entire Sudoku board. */
    private Node root;
    /** Is the sudoku completed*/
    private final SudokuGenerator generator = new SudokuGenerator();
    /** Returns the sudokugenerator */
    public SudokuGenerator getGenerator() {return this.generator;}

    /** Returns the root node of the Sudoku board. */
    Node getroot() { return this.root; }

    /**
     * Constructs a Sudoku board.
     * <ul>
     *     <li>Creates an empty hierarchical structure (sections and cells).</li>
     *     <li>Fills two random valid values per section as initial clues.</li>
     * </ul>
     */
    public Board() {
        root = new Node(0.0f);
        root.setValor("");
        initializeStructure();
        fillInitialValues();
    }

    /** IDs representing each Sudoku section (1–6). */
   private  List<Float> idSectionsBoard = Arrays.asList(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);

    /** IDs representing each cell inside a section (0.1–0.6). the idea is that idChild = 1.2 for example, father is 1.0, child is 1.2 */
    List<Float> idCellsBoard = Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f);

    // ----------------------------------------------------------
    // Initialization
    // ----------------------------------------------------------

    /**
     * Builds the logical structure of the Sudoku board:
     * 6 section nodes, each containing 6 empty cell nodes.
     */
    private void initializeStructure() {
        root.getChildren().clear();

        for (Float idSection : idSectionsBoard) {
            Node sectionNode = new Node(idSection);
            root.addCHildren(sectionNode);

            for (Float boxId : idCellsBoard) {
                Node cellNode = new Node(idSection + boxId);
                sectionNode.addCHildren(cellNode);
            }
        }
    }

    /**
     * Fills the board with initial values based on a valid Sudoku solution.
     * <ul>
     *     <li>Uses {@link SudokuGenerator} to create a solved Sudoku.</li>
     *     <li>Randomly selects two cells in each section to show as clues.</li>
     *     <li>Marks those cells as initial (fixed) values.</li>
     * </ul>
     */
    private void fillInitialValues() {
         // Generates a complete Sudoku

        for (int i = 0; i < root.getChildren().size(); i++) {
            Node section = root.getChildren().get(i);
            List<Integer> sectionValues = generator.getSection(i + 1); // Sections 1–6
            List<Node> children = section.getChildren();

            List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4, 5);
            Collections.shuffle(indices);
            List<Integer> chosenIndices = indices.subList(0, 2);

            for (int idx : chosenIndices) {
                Node child = children.get(idx);
                int solvedValue = sectionValues.get(idx);
                setNodeValue(section.getId(), child.getId(), String.valueOf(solvedValue));
                child.setIsInitialValue(true);
            }
        }
    }

    // ----------------------------------------------------------
    // ID Parsing
    // ----------------------------------------------------------

    /**
     * Extracts the parent (section) ID from a text field identifier.
     * <p>Example: "P1C1" → section ID = 1.0f</p>
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
     * <p>Example: "P1C3" → cell ID = 1.3f</p>
     *
     * @param textFieldId the ID of the text field (e.g., "t13")
     * @return the cell ID as a float
     */
    private Float getchildId(String textFieldId) {
        String idChildren = textFieldId.charAt(1) + "." + textFieldId.charAt(3);
        return Float.parseFloat(idChildren);
    }

    // ----------------------------------------------------------
    // Node Value Handling
    // ----------------------------------------------------------

    /**
     * Sets the value of a cell node based on the corresponding TextField ID.
     *
     * @param idTextfield the TextField ID (e.g., "t13")
     * @param value the new value to assign
     */
    public void setNodeValue(String idTextfield, String value) {
        Float fatherId = getfatherId(idTextfield);
        Float childId = getchildId(idTextfield);
        for (Node nodeFather : root.getChildren()) {
            if (nodeFather.getId().equals(fatherId)) {
                for (Node nodeChild : nodeFather.getChildren()) {
                    if (nodeChild.getId().equals(childId)) {
                        nodeChild.setValor(value);
                    }
                }
            }
        }
    }

    /**
     * Sets the value of a cell node directly by section and child ID.
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
     *     <li>No repetition in the same section (block).</li>
     *     <li>No repetition in the same column.</li>
     *     <li>No repetition in the same row.</li>
     * </ul>
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
     *<ul>
     *      *     <li>No repetition in the same section (block).</li>
     *      *     <li>No repetition in the same column.</li>
     *      *     <li>No repetition in the same row.</li>
     *      * </ul>
     * @param textFieldId the TextField ID (e.g., "t13")
     * @return {@code true} if the value does not violate Sudoku rules
     */
    public boolean validateInput(String textFieldId) {
        String childValue = getValueNode(textFieldId);
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
     * @param childValue the value to validate
     * @return {@code true} if the value is not repeated within the section
     */
    private boolean validateSection(Float fatherId, String childValue) {
        List<String> sectionValues = new ArrayList<>();
        for (Node nodo : root.getChildren()) {
            if (nodo.getId().equals(fatherId)) {
                for (Node nodo2 : nodo.getChildren()) {
                    if (!nodo2.getValue().equals(" ")) {
                        sectionValues.add(nodo2.getValue());
                    }
                }
            }
        }
        long count = sectionValues.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }

    /**
     * Validates that a value is not repeated within the same Sudoku column.
     *
     * @param fatherId   the section ID
     * @param childId the cell ID
     * @param childValue the value to validate
     * @return {@code true} if the column is valid
     */
    private boolean validateColumn(Float fatherId, Float childId, String childValue) {
        int initial = (fatherId % 2 == 0) ? 1 : 0;
        Float columnConstantPerSection = 0.3f;
        List<Node> nodes = root.getChildren();
        List<Node> columnNodes = new ArrayList<>();
        List<String> valuesColumn = new ArrayList<>();
        Float columnIdentifier = (childId - fatherId < 0.4)
                ? childId - fatherId
                : childId - fatherId - columnConstantPerSection;

        for (int i = initial; i < 6; i += 2) {
            columnNodes.add(nodes.get(i));
        }

        for (Node nodeFather : columnNodes) {
            for (Node nodeChildren : nodeFather.getChildren()) {
                final float EPSILON = 0.0001f;
                float diff1 = nodeChildren.getId() - nodeFather.getId();
                float diff2 = nodeChildren.getId() - nodeFather.getId() - columnConstantPerSection;

                if (Math.abs(diff1 - columnIdentifier) < EPSILON ||
                        Math.abs(diff2 - columnIdentifier) < EPSILON) {
                    if (!nodeChildren.getValue().equals(" ")) {
                        valuesColumn.add(nodeChildren.getValue());
                    }
                }
            }
        }
        long count = valuesColumn.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }

    /**
     * Validates that a value is not repeated within the same Sudoku row.
     *
     * @param fatherId   the section ID
     * @param childId the cell ID
     * @param childValue the value to validate
     * @return {@code true} if the cell with respect the row
     */
    private boolean validateRow(Float fatherId, Float childId, String childValue) {
        List<Float> rowConstants = (childId - fatherId < 0.4f)
                ? Arrays.asList(0.1f, 0.2f, 0.3f)
                : Arrays.asList(0.4f, 0.5f, 0.6f);

        List<String> valuesRow = new ArrayList<>();
        Float neighborId = (fatherId % 2.0f == 0) ? fatherId - 1.0f : fatherId + 1.0f;
        Node nodeFather = root.getChildren().get(Math.round(fatherId) - 1);
        Node nodeNeighbor = root.getChildren().get(Math.round(neighborId) - 1);
        final float EPSILON = 0.0001f;

        for (Node nodeChildren : nodeFather.getChildren()) {
            float diff = nodeChildren.getId() - nodeFather.getId();
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - diff) < EPSILON);
            if (matches && !nodeChildren.getValue().trim().isEmpty()) {
                valuesRow.add(nodeChildren.getValue().trim());
            }
        }
        for (Node nodeChild : nodeNeighbor.getChildren()) {
            float diff = nodeChild.getId() - nodeNeighbor.getId();
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - diff) < EPSILON);
            if (matches && !nodeChild.getValue().trim().isEmpty()) {
                valuesRow.add(nodeChild.getValue().trim());
            }
        }

        long count = valuesRow.stream().filter(v -> v.equals(childValue)).count();
        return count <= 1;
    }

    // ----------------------------------------------------------
    // Node Access
    // ----------------------------------------------------------

    /**
     * Returns the string value of a node by its TextField ID.
     *
     * @param textFieldId the TextField ID (e.g., "P1C3")
     * @return the value stored in that node
     */
    public String getValueNode(String textFieldId) {
        Float childId = getchildId(textFieldId);
        Float fatherId = getfatherId(textFieldId);
        for (Node nodeFather : root.getChildren()) {
            for (Node nodeChildren : nodeFather.getChildren()) {
                if (nodeChildren.getId().equals(childId)) {
                    return nodeChildren.getValue();
                }
            }
        }
        return "";
    }

    /** Returns the root node. */
    protected Node getRoot() {
        return root;
    }

    /**
     * Finds a specific node by parent and child ID.
     *
     * @param fatherId   the section ID
     * @param childId the cell ID
     * @return the matching node or {@code null} if not found
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
     *
     * @param textFieldId the TextField ID (e.g., "P1C2")
     * @return the corresponding {@link Node} or {@code null} if not found
     */
    public Node getNode(String textFieldId) {
        Float childId = getchildId(textFieldId);
        Float fatherId = getfatherId(textFieldId);
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
}


