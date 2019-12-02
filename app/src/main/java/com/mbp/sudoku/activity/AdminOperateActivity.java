package com.mbp.sudoku.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mbp.sudoku.R;
import com.mbp.sudoku.adapter.MapAdapter;
import com.mbp.sudoku.entity.GameMapEntity;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.GenerateUtil;

import java.util.ArrayList;
import java.util.List;

public class AdminOperateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operate_layout);

        Button button_addLevel = findViewById(R.id.btn_addLevel);
        Button button_deleteLevel = findViewById(R.id.btn_deleteLevel);
        EditText editText_addNumber = findViewById(R.id.et_addNumber);
        EditText editText_startNumber = findViewById(R.id.et_startNumber);
        EditText editText_endNumber = findViewById(R.id.et_endNumber);

        button_addLevel.setOnClickListener(view -> {
            int levelNumber = Integer.valueOf(editText_addNumber.getText().toString());
            if (levelNumber == 0){
                Toast.makeText(this,"请输入生成关卡的数量!",Toast.LENGTH_SHORT).show();
            }
            else {
                //创建数据库
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                //插入数据
                for (int i = 0; i < levelNumber; i++) {
                    GenerateUtil generateUtil = new GenerateUtil();
                    Gson gson = new Gson();
                    int[][]a = generateUtil.getMap();
                    String firstMap = gson.toJson(a);
                    int[][]b = generateUtil.maskCells(a);
                    String gameMap = gson.toJson(b);
                    ContentValues values = new ContentValues();
                    values.put("gameMap", firstMap);
                    values.put("mapStatus", gameMap);
                    values.put("goodTime", "");
                    values.put("status", 0);
                    database.insert("gamemap", null, values);
                }
                Toast.makeText(this,"成功生成" + levelNumber + "张地图",Toast.LENGTH_SHORT).show();
            }
        });

        button_deleteLevel.setOnClickListener(view -> {
            int startNumber = Integer.valueOf(editText_startNumber.getText().toString());
            int endNumber = Integer.valueOf(editText_endNumber.getText().toString());
            if (endNumber == 0){
                Toast.makeText(this,"请输入删除关卡的数量!",Toast.LENGTH_SHORT).show();
            }
            else {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                for (int i = startNumber; i <= endNumber ; i++) {
                    database.delete("gamemap","id = ?",new String[]{String.valueOf(i)});
                }
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }
            Log.d("AdminOperateActivity","删除数据库");
        });
    }
}
