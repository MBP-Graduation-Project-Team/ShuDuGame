package com.mbp.sudoku.util;

public class PointNumber {


    public String getText(int i, int j) {
        Integer index = 0;

        index = 3*i+j+1;

        String text = index.toString();

        return text;
    }
    public int getPoint(int i, int j) {

        int number = 3*i+j+1;;





        return number;
    }


}