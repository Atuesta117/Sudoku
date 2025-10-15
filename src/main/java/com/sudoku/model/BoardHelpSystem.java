package com.sudoku.model;
import java.util.*;


public class BoardHelpSystem  extends Board{

    private Stack<EmptyCell> emptyCellsStack;

    public BoardHelpSystem(Board board) {
        super();
        this.emptyCellsStack = new Stack<>();
    }

    //Internal class to manege all the empty cells (i like this approach)

    private class EmptyCell{
        float fatherId;
        float childrenId;

        EmptyCell(float fatherId, float childrenId){
            this.fatherId = fatherId;
            this.childrenId = childrenId;
        }
    }

    //Here we iterate over the n-ary tree in order to find empty cells and add them to the stack

    private void loadEmptyCellsToStack(){

        //Here we acces the root of the board (the father of all children)
        Node root = getRoot();

        for (Node section : root.getChildren()){
            for(Node cell : section.getChildren()){
                if(cell.getValue().trim().isEmpty() || cell.getValue().equals(" ")){
                    emptyCellsStack.push(
                            new EmptyCell(section.getId(), cell.getId())
                    );
                }
            }
        }

    }

    public boolean giveHelp(){

        if(emptyCellsStack.isEmpty()){
            //there are no more cells to help the user
            return false;
        }

        EmptyCell currentCell = emptyCellsStack.pop();

        //we pass throught all values from 1 to 6, but we will allow them later
        for (int number = 1; number <= 6; number++){
            String strValue = String.valueOf(number);

            //this is the moment we study each value and decide if its valid or not
            if(isValid(currentCell.fatherId, currentCell.childrenId, strValue)){
                setNodeValue(currentCell.fatherId, currentCell.childrenId, strValue);
                return true;
            };

        }

        return false;


    }


    private boolean isValid(float fatherId, float childrenId, String value){

        //temporaly we set the value, in order to check it out in the following lines
        setNodeValue(fatherId, childrenId,  value);

        //using the protected method of validation
        boolean valid = validateInput(fatherId, childrenId, value);

        if(!valid){
            setNodeValue(fatherId,childrenId, "");
        }

        return valid;

    }

    public boolean haveMoreEmptyCells(){
        return !emptyCellsStack.isEmpty();
    }

}

