package com.mbp.sudoku.util;

import com.mbp.sudoku.entity.GameMap;

import java.util.List;

public class PointNumber {
    private static String passNumber;
    private static String countNumber;
    private static List<GameMap> mapList;


    public String getText(int i, int j) {
        int index = 3*i+j+1;
        return Integer.toString(index);
    }
    public int getPoint(int i, int j) {

        return 3*i+j+1;
    }

    public static String getPassNumber() {
        return passNumber;
    }

    public static void setPassNumber(String passNumber) {
        PointNumber.passNumber = passNumber;
    }

    public static String getCountNumber() {
        return countNumber;
    }

    public static void setCountNumber(String countNumber) {
        PointNumber.countNumber = countNumber;
    }

    public static List<GameMap> getMapList() {
        return mapList;
    }

    public static void setMapList(List<GameMap> mapList) {
        PointNumber.mapList = mapList;
    }
}