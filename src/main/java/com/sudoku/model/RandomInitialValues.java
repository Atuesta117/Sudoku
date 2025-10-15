package com.sudoku.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomInitialValues {
    private List<Float> idChildrens = Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f);
    private List<Float> idFather = Arrays.asList(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f);
    private List<String> values = Arrays.asList("1","2","3","4","5","6");

    Random rand = new Random();
    public RandomInitialValues(){

    }



    public Float getRandomChildrenId( Float idFather){
            int index = rand.nextInt(idChildrens.size());
            return idChildrens.get(index) + idFather;
    }
    public String  getRandonValue(){
        int index = rand.nextInt(values.size());
        return values.get(index);

    }


}