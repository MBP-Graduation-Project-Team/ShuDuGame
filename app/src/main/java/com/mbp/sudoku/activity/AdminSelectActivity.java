package com.mbp.sudoku.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;
import com.mbp.sudoku.adapter.MapAdapter;
import com.mbp.sudoku.entity.GameMapEntity;
import com.mbp.sudoku.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "test.db", null, 1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query("gamemap", null, null, null, null, null, null);
        List<GameMapEntity> gameMapEntities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                GameMapEntity gameMapEntity = new GameMapEntity();
                int id = cursor.getInt(0);
                String map = cursor.getString(1);
                String gameMap = cursor.getString(2);
                String time = cursor.getString(3);
                int status = cursor.getInt(4);
                gameMapEntity.setId(id);
                gameMapEntity.setGameMap(map);
                gameMapEntity.setMapStatus(gameMap);
                gameMapEntity.setGoodTime(time);
                gameMapEntity.setStatus(status);
                /*Log.i("id", String.valueOf(id));
                Log.i("map", map);
                Log.i("gameMap", gameMap);
                Log.i("time", time);
                Log.i("status", String.valueOf(status));*/
                gameMapEntities.add(gameMapEntity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        /*for (int i = 0; i < gameMapEntities.size(); i++) {
            System.out.println(gameMapEntities.get(i).toString());
        }*/
        MapAdapter mapAdapter = new MapAdapter(AdminSelectActivity.this, R.layout.level_layout, gameMapEntities);
        ListView listView = findViewById(R.id.list_map);
        listView.setAdapter(mapAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            GameMapEntity gameMapEntity = gameMapEntities.get(i);
            Intent intent = new Intent(AdminSelectActivity.this,GameActivity.class);
            intent.putExtra("level",gameMapEntity.getId());
            startActivity(intent);
        });
    }
}
