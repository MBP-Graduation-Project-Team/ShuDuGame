package com.mbp.sudoku.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.GenerateUtil;

public class AdminGenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_layout);
        Button button1 = findViewById(R.id.btn_generate);
        EditText editText1 = findViewById(R.id.level_number);
        button1.setOnClickListener(view -> {
            int levelNumber = Integer.valueOf(editText1.getText().toString());
            if (levelNumber == 0){
                Toast.makeText(this,"请输入关卡数量!",Toast.LENGTH_SHORT).show();
            }
            else {
                //创建数据库
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                //生成游戏地图
                for (int i = 0; i < levelNumber; i++) {
                    GenerateUtil generateUtil = new GenerateUtil();
                    Gson gson = new Gson();
                    int[][]a = generateUtil.getMap();
                    String firstMap = gson.toJson(a);
                    int[][]b = generateUtil.maskCells(a);
                    String gameMap = gson.toJson(b);
                    ContentValues values = new ContentValues();
                    values.put("original_map", firstMap);
                    values.put("game_map", gameMap);
                    database.insert("tb_game_map", null, values);
                }

                //初始化游戏最后一次保存进度
                ContentValues values = new ContentValues();
                values.put("level", 0);
                database.insert("tb_end_speed", null, values);

                Cursor cursor = database.query("tb_game_map",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        int id = cursor.getInt(0);
                        String map = cursor.getString(1);
                        Log.i("level", String.valueOf(id));
                        Log.i("map", map);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
