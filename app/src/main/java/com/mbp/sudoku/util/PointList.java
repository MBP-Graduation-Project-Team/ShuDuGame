package com.mbp.sudoku.util;

import com.mbp.sudoku.entity.GameMapEntity;

import java.util.List;

public class PointList {

    private List<GameMapEntity> mapList;

    /*public static List<Map>  addList(){
        String time = "09:09";
        int point=3;
        List<Map> gameTime=new ArrayList<>();
        for (int i = 0; i < point; i++) {
            Map map = new Map();
            map.setId(i+1);
            map.setTime(time);
            gameTime.add(map);
        }
        return gameTime;
    }*/

    public List<GameMapEntity> getMapList() {
        return mapList;
    }

    public void setMapList(List<GameMapEntity> mapList) {
        this.mapList = mapList;
    }
}
