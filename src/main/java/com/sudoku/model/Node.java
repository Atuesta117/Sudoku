package com.sudoku.model;

import java.util.ArrayList;

public class Node {
    private Float id;
    private String valor;
    private ArrayList<Node> children;
    public Node(Float id) {
        this.id = id;
        this.children = new ArrayList<>();
        this.valor = " ";
    }
    public Float getId() {return id;}
    public void setId(Float id) {this.id = id;}
    public String getValue() {return valor;}
    public void setValor(String valor) {this.valor = valor;}
    public ArrayList<Node> getChildren() {return children;}
    public void addCHildren(Node nodo) {this.children.add(nodo);}
}
