package com.mbp.sudoku.util;

public class PointNumber {
    private static String passNumber;
    private static String countNumber;

    public String getText(int i, int j) {
        Integer index = 0;

        index = 3*i+j+1;

        String text = index.toString();

        return text;
    }
    public int getPoint(int i, int j) {

        int number = 3*i+j+1;





        return number;
    }

    public String getPassNumber() {
        return passNumber;
    }

    public void setPassNumber(String passNumber) {
        this.passNumber = passNumber;
    }

    public String getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(String countNumber) {
        this.countNumber = countNumber;
    }
}