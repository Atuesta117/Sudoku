package com.sudoku.model;

import java.util.ArrayList;

/**
 * Represents a node within the Sudoku board tree structure.
 * Each node can have an identifier, a value, and a list of child nodes.
 *
 * <p>Example of hierarchy:
 * <ul>
 *   <li>The root node represents the entire Sudoku board.</li>
 *   <li>The root’s children represent the 2x3 sections of the board.</li>
 *   <li>The children of each section represent individual cells.</li>
 * </ul>
 */
public class Node {

    /** Unique identifier of the node. */
    private Float id;

    /** Value assigned to this node (e.g., a number from 1–6 or empty). */
    private String valor;

    /** List of child nodes (e.g., cells inside a section). */
    private ArrayList<Node> children;

    /** Indicates whether the value was part of the initial Sudoku puzzle. */
    private boolean isInitialValue;

    /**
     * Creates a new Node with the specified identifier.
     *
     * @param id the identifier of the node
     */
    public Node(Float id) {
        this.id = id;
        this.children = new ArrayList<>();
        this.valor = " ";
        this.isInitialValue = false;
    }

    /**
     * Gets the node’s identifier.
     *
     * @return the node ID
     */
    public Float getId() { return id; }

    /**
     * Sets the node’s identifier.
     *
     * @param id the new node ID
     */
    public void setId(Float id) { this.id = id; }

    /**
     * Gets the current value of this node.
     *
     * @return the node’s value
     */
    public String getValue() { return valor; }

    /**
     * Sets a new value for this node.
     *
     * @param valor the value to assign
     */
    public void setValor(String valor) { this.valor = valor; }

    /**
     * Returns the list of child nodes.
     *
     * @return the list of children
     */
    public ArrayList<Node> getChildren() { return children; }

    /**
     * Adds a child node to this node.
     *
     * @param node the child node to add
     */
    public void addCHildren(Node node) { this.children.add(node); }

    /**
     * Checks whether this node contains an initial Sudoku value.
     *
     * @return true if it’s an initial value; false otherwise
     */
    public boolean getIsInitialValue() { return isInitialValue; }

    /**
     * Sets whether this node represents an initial Sudoku value.
     *
     * @param isInitialValue true if it’s an initial Sudoku cell
     */
    public void setIsInitialValue(boolean isInitialValue) { this.isInitialValue = isInitialValue; }
}

