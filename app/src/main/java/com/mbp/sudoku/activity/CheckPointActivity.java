package com.mbp.sudoku.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.MainActivity;
import com.mbp.sudoku.R;
import com.mbp.sudoku.entity.GameMapEntity;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.PointNumber;
import com.mbp.sudoku.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class CheckPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkpoint_layout);
        //返回按钮
        Button button_back = findViewById(R.id.return_button);
        button_back.setOnClickListener(view -> {
            Intent intent = new Intent(CheckPointActivity.this, MainActivity.class);
            startActivity(intent);
        });

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query("tb_game_map",null,null,null,null,null,null);
        List<GameMapEntity> gameMapEntities = new ArrayList<>();
        TimeUtil timeUtil = new TimeUtil();
        if (cursor.moveToFirst()){
            cursor.getCount();
            do {
                GameMapEntity gameMapEntity = new GameMapEntity();
                int id = cursor.getInt(0);
                gameMapEntity.setId(id);
                int goodTime = cursor.getInt(3);
                gameMapEntity.setGoodTime(timeUtil.getStringTime(goodTime));
                Log.i("id", String.valueOf(id));
                Log.i("goodTime", timeUtil.getStringTime(goodTime));
                gameMapEntities.add(gameMapEntity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        PointNumber.setMapList(gameMapEntities);
        PointNumber.setCountNumber(String.valueOf(cursor.getCount()));
        Cursor cursor2 = database.rawQuery("select level from tb_game_map where status = ?",new String[]{"1"});
        Log.d("count",String.valueOf(cursor2.getCount()));
        PointNumber.setPassNumber(String.valueOf(cursor2.getCount()));
        cursor2.close();
    }
}
