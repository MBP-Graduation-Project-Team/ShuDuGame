package com.mbp.sudoku.util;

import com.mbp.sudoku.entity.GameMapEntity;

import java.util.List;

public class PointList {

    private static List<GameMapEntity> mapList;


    public List<GameMapEntity> getMapList() {
        return mapList;
    }

    public void setMapList(List<GameMapEntity> mapList) {
        this.mapList = mapList;
    }
}
