package com.sudoku.model;

import java.util.*;

public class Board{
    private Node raiz;
    RandomInitialValues randomInitialValues = new RandomInitialValues();
    public Board() {
        raiz = new Node(0.0f);
        raiz.setValor("");
        initializeSections();
    }
    List<Float> idSectionsBoard = Arrays.asList( 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
    List<Float> idBoxesBoard = Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f);


    private void setNode( Float father ,Float children){
        if(father.equals(0.0f)){
            Node nodo = new Node(children);
            raiz.addCHildren(nodo);

        }
        else{
            for(Node nodo : raiz.getChildren()){
                if(nodo.getId().equals(father)){
                    Node nodo2 = new Node(children+father);
                    nodo.addCHildren(nodo2);
                }
            }
        }

    }
    public void initializeSections(){
        for(Float idSection : idSectionsBoard){
            setNode(0.0f, idSection);

        }

        for(int i=0; i < 6; i++) {
            for(int j=0; j < 6; j++) {
                setNode(idSectionsBoard.get(i), idBoxesBoard.get(j));
            }
            int auxiliarCounter = 0;
            List<Float> usedChildren = new ArrayList<>();

            while (auxiliarCounter != 2) {
                Float randomChild = randomInitialValues.getRandomChildrenId(idSectionsBoard.get(i));
                if (usedChildren.contains(randomChild)) continue;
                String randomValue = randomInitialValues.getRandonValue();

                setNodeValue(idSectionsBoard.get(i), randomChild, randomValue );
                if (validateInput(idSectionsBoard.get(i), randomChild, randomValue)) {
                    auxiliarCounter++;
                    usedChildren.add(randomChild);
                    Node nodo = getNode(idSectionsBoard.get(i), randomChild);
                    nodo.setIsInitialValue(true);

                } else {
                    setNodeValue(idSectionsBoard.get(i), randomChild, " ");
                }
            }


        }


    }

    private Float getFatherid(String textFieldId){

        String sudokuSection = textFieldId.charAt(1) + "";
        sudokuSection += ".0";
        return Float.parseFloat(sudokuSection);
    }

    private Float getChildrenid(String textFieldId){
        String idChildren = "";

                idChildren += textFieldId.charAt(1) + "";

                idChildren += ".";
                String aux= textFieldId.charAt(3) + "";
                idChildren += aux;
        return Float.parseFloat(idChildren);
    }

    public void setNodeValue(String idTextfield, String value){
        Float fatherId = getFatherid(idTextfield);
        Float childrenId = getChildrenid(idTextfield);
        for(Node nodeFather: raiz.getChildren()){
            if(nodeFather.getId().equals(fatherId)){
                for(Node nodeChild: nodeFather.getChildren()){
                    if(nodeChild.getId().equals(childrenId)){
                        nodeChild.setValor(value);
                    }
                }
            }
        }
    }

    private void setNodeValue(Float fatherId, Float childrenId, String value){
       for(Node nodeFather: raiz.getChildren()){
            if(nodeFather.getId().equals(fatherId)){
                for(Node nodeChild: nodeFather.getChildren()){
                    if(nodeChild.getId().equals(childrenId)){
                        nodeChild.setValor(value);
                    }
                }
            }
        }
    }
    private boolean validateInput(Float fatherid, Float childrenid, String value){

        return validateSection(fatherid,value) && validateColumn(fatherid,  childrenid, value) && validateRow(fatherid,  childrenid, value);
    }

    public boolean validateInput(String textFieldId){
        String childValue = getValueNode(textFieldId);
        Float childrenId = getChildrenid(textFieldId);
        Float fatherId = getFatherid(textFieldId);
        return validateSection(fatherId, childValue) && validateColumn(fatherId,  childrenId, childValue) && validateRow(fatherId,  childrenId, childValue);
    }

    private boolean validateSection(Float fatherid, String childValue){
        List<String> sectionValues = new ArrayList<>();
            for(Node nodo : raiz.getChildren()){
                if(nodo.getId().equals(fatherid)){

                    for(Node nodo2 : nodo.getChildren()){
                            if(!nodo2.getValue().equals(" ")){
                                sectionValues.add(nodo2.getValue());
                            }

                    }

                }
            }
        long count = sectionValues.stream()//go through the entire list
                .filter(v -> v.equals(childValue))//filter the values that are equals to the childValue (the one we are evaluating)
                .count();//we count it

        return count <= 1;//if there are more than 1, It's because this is repeated

    }

    private boolean validateColumn(Float fatherId, Float childrenId, String childValue){

        int initial = (fatherId%2 == 0)? 1:0;//The left sections are in positions of the root array from 0 to 5 with an increase of two, that is, 1,2,4 while the right ones are 1,3,5. The idea of this is that the for starts from 0 or 1 depending on whether a column that is part of the left or right sections is going to be evaluated.
        Float columnConstantPerSection = 0.3f; //The idea is that if, for example, you want to check a line with respect to its sudoku column, you try to take the "mini" column within the section
        List<Node> nodes = raiz.getChildren();
        List<Node> columnNodes = new ArrayList<>();//nodes vinculated with the column to assess
        List<String> valuesColumn = new ArrayList<>();
        Float columnIdentifier = (childrenId-fatherId <0.4)? childrenId-fatherId: childrenId-fatherId-columnConstantPerSection;


        for(int i = initial; i < 6; i += 2){
            columnNodes.add(nodes.get(i));

        }

        //now we have the sections that are vinculated with the column, now, I will take the "mini" column of every section
        for(Node nodeFather : columnNodes){
            List<Node> children = nodeFather.getChildren();
            for(Node nodeChildren : children){
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
        long count = valuesColumn.stream()
                .filter(v -> v.equals(childValue))
                .count();

        return count <= 1;

    }
    private boolean validateRow(Float fatherId, Float childrenId, String childValue){

        List<Float> rowConstants = ( childrenId - fatherId < 0.4f)? Arrays.asList(0.1f,0.2f,0.3f): Arrays.asList(0.4f,0.5f,0.6f);
        List<String> valuesRow = new ArrayList<>();
        Float neighborId = (fatherId%2.0f ==0)? fatherId-1.0f: fatherId+ 1.0f;
        int fatherInt = Math.round(fatherId);
        int neighborInt = Math.round(neighborId);
        Node nodeFather = raiz.getChildren().get(fatherInt-1);
        Node nodeNeighbor = raiz.getChildren().get(neighborInt-1);
        final float EPSILON = 0.0001f;

        for (Node nodeChildren: nodeFather.getChildren()){

            float diff = nodeChildren.getId() - nodeFather.getId();
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - diff) < EPSILON);

            if( matches && !nodeChildren.getValue().trim().isEmpty() ){
                valuesRow.add(nodeChildren.getValue().trim());

            }
        }
        for(Node nodeChild : nodeNeighbor.getChildren()){


            float diff = nodeChild.getId() - nodeNeighbor.getId();
            boolean matches = rowConstants.stream().anyMatch(c -> Math.abs(c - diff) < EPSILON);
            if(matches  &&  !nodeChild.getValue().trim().isEmpty()){
                valuesRow.add(nodeChild.getValue().trim());


            }
        }


        long count = valuesRow.stream()
                .filter(v -> v.equals(childValue))
                .count();

        return count <= 1;

    }

    public String getValueNode(String textFieldId){
        Float childrenid = getChildrenid(textFieldId);
        Float fatherid = getFatherid(textFieldId);
        String value = "";
        for(Node nodeFather : raiz.getChildren()){
            for(Node nodeChildren : nodeFather.getChildren()){
                if(nodeChildren.getId().equals(childrenid)){

                     value += nodeChildren.getValue();

                }

            }
        }

        return value;
    }

    private Node getNode(Float fatherId, Float childrenId){
        for(int i = 0; i < raiz.getChildren().size(); i++){
                if(raiz.getChildren().get(i).getId().equals(fatherId)){
                    for(Node nodeChildren : raiz.getChildren().get(i).getChildren()){
                        if(nodeChildren.getId().equals(childrenId)){return  nodeChildren;}
                    }

            }

        }
        return null;


    }


    public Node getNode(String textFieldId){
        Float childrenId = getChildrenid(textFieldId);
        Float fatherId = getFatherid(textFieldId);
        for(int i = 0; i < raiz.getChildren().size(); i++){
            if(raiz.getChildren().get(i).getId().equals(fatherId)){
                for(Node nodeChildren : raiz.getChildren().get(i).getChildren()){
                    if(nodeChildren.getId().equals(childrenId)){return  nodeChildren;}
                }

            }

        }
        return null;


    }
}

