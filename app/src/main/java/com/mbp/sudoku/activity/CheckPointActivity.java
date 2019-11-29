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

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"sudoku.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query("gamemap",null,null,null,null,null,null);
        List<GameMapEntity> gameMapEntities = new ArrayList<>();
        if (cursor.moveToFirst()){
            cursor.getCount();
            do {
                GameMapEntity gameMapEntity = new GameMapEntity();
                int id = cursor.getInt(0);
                gameMapEntity.setId(id);
                String goodTime = cursor.getString(3).isEmpty()? "00:00" : cursor.getString(3);
                gameMapEntity.setGoodTime(goodTime);
                Log.i("id", String.valueOf(id));
                Log.i("goodTime", goodTime);
                gameMapEntities.add(gameMapEntity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        PointNumber.setMapList(gameMapEntities);
        PointNumber.setCountNumber(String.valueOf(cursor.getCount()));
        Cursor cursor2 = database.rawQuery("select * from gamemap where status = ?",new String[]{"1"});
        Log.d("count",String.valueOf(cursor2.getCount()));
        cursor2.close();
        PointNumber.setPassNumber("0");
    }
}
