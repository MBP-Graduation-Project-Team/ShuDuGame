package com.mbp.sudoku.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;
import com.mbp.sudoku.entity.GameMap;
import com.mbp.sudoku.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level_layout);
        List<GameMap> gameMapList = initList();
        LinearLayout linearLayout = findViewById(R.id.select_level);
        for (GameMap gameMap:gameMapList) {
            Button button = new Button(this);
            button.setText(String.valueOf(gameMap.getId()));
            button.setWidth(150);
            button.setHeight(150);
            linearLayout.addView(button);
        }
    }

    public List<GameMap> initList(){
        List<GameMap> gameMapList = new ArrayList<>();
        TimeUtil timeUtil = new TimeUtil();
        for (int i = 0; i < 5; i++) {
            GameMap gameMap = new GameMap();
            gameMap.setId(i + 1);
            gameMap.setGoodTime(timeUtil.getStringTime(132));
            if (i == 0){
                gameMap.setStatus(0);
            }
            else {
                gameMap.setStatus(-1);
            }
            gameMapList.add(gameMap);
        }
        return gameMapList;
    }
}
