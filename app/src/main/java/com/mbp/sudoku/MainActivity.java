package com.mbp.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.mbp.sudoku.activity.CheckPointActivity;
import com.mbp.sudoku.activity.GameActivity;
import com.mbp.sudoku.test.TestU;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.GenerateUtil;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //开始游戏按钮
        Button btn_start = findViewById(R.id.game_begin);
        //继续游戏按钮
        Button btn_continue = findViewById(R.id.game_continue);

        //开始游戏按钮监听器
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckPointActivity.class);
                startActivity(intent);
            }
        });

        //继续游戏按钮监听器
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"sudoku.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();

        /*for (int i = 0; i < 1; i++) {
            GenerateUtil generateUtil = new GenerateUtil();
            Gson gson = new Gson();
            int[][]a = TestU.getMap();
            String firstMap = gson.toJson(a);
            int[][]b = generateUtil.maskCells(a);
            String gameMap = gson.toJson(b);
            ContentValues values = new ContentValues();
            values.put("gameMap", firstMap);
            values.put("mapStatus", gameMap);
            values.put("goodTime", "");
            values.put("status", 0);
            database.insert("gamemap", null, values);
        }*/
        /*Cursor cursor = database.query("gamemap",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String gameMap = cursor.getString(1);
                String mapStatus = cursor.getString(2);
                String goodTime = cursor.getString(3);
                int status = cursor.getInt(4);
                Log.i("id", String.valueOf(id));
                Log.i("gameMap", gameMap);
                Log.i("mapStatus", mapStatus);
                Log.i("goodTime", goodTime);
                Log.i("status", String.valueOf(status));
            }while (cursor.moveToNext());
        }
        cursor.close();*/
//        database.delete("gamemap",null,null);
    }
}
