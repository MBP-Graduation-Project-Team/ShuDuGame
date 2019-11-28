package com.mbp.sudoku.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;
import com.mbp.sudoku.entity.GameMapEntity;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.PointNumber;

import java.util.ArrayList;
import java.util.List;

public class CheckPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpoint_layout);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"sudoku.db",null,2);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query("gamemap",null,null,null,null,null,null);
        List<GameMapEntity> gameMapEntities = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                GameMapEntity gameMapEntity = new GameMapEntity();
                int id = cursor.getInt(0);
                gameMapEntity.setId(id);
                String goodTime = cursor.getString(3);
                gameMapEntity.setGoodTime(goodTime);
                Log.i("id", String.valueOf(id));
                Log.i("goodTime", goodTime);
                gameMapEntities.add(gameMapEntity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        PointNumber.setMapList(gameMapEntities);
        PointNumber.setCountNumber("9");
        PointNumber.setPassNumber("9");
    }
}
