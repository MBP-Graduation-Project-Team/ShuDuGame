package com.mbp.sudoku.util;

import com.mbp.sudoku.util.Map;

import java.util.ArrayList;
import java.util.List;

public class PointList {


    public static List<Map>  addList(){
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
    }


   }
