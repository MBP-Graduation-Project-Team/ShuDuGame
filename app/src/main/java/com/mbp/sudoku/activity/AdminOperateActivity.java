package com.mbp.sudoku.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.GenerateMapUtil;

/**
 * 管理员操作数据库活动类
 */
public class AdminOperateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operate_layout);

        //添加按钮
        Button button_addLevel = findViewById(R.id.btn_addLevel);
        //删除按钮
        Button button_deleteLevel = findViewById(R.id.btn_deleteLevel);
        //添加数量输入框
        EditText editText_addNumber = findViewById(R.id.et_addNumber);
        //删除数量起始点输入框
        EditText editText_startNumber = findViewById(R.id.et_startNumber);
        //删除数量结束点输入框
        EditText editText_endNumber = findViewById(R.id.et_endNumber);

        //添加按钮监听事件
        button_addLevel.setOnClickListener(view -> {
            //添加关卡数量
            int levelNumber = Integer.valueOf(editText_addNumber.getText().toString());
            //添加关卡数量为0
            if (levelNumber == 0){
                Toast.makeText(this,"请输入生成关卡的数量!",Toast.LENGTH_SHORT).show();
            }
            else {
                //创建数据库
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                //插入数据
                for (int i = 0; i < levelNumber; i++) {
                    GenerateMapUtil generateUtil = new GenerateMapUtil();
                    Gson gson = new Gson();
                    int[][]a = generateUtil.getMap();
                    String firstMap = gson.toJson(a);
                    int[][]b = generateUtil.maskCells(a);
                    String gameMap = gson.toJson(b);
                    ContentValues values = new ContentValues();
                    values.put("original_map", firstMap);
                    values.put("game_map", gameMap);
                    database.insert("tb_game_map",   null, values);
                }
                Toast.makeText(this,"成功生成" + levelNumber + "张地图",Toast.LENGTH_SHORT).show();
            }
        });

        //删除按钮监听事件
        button_deleteLevel.setOnClickListener(view -> {
            //删除数量起始点
            int startNumber = Integer.valueOf(editText_startNumber.getText().toString());
            //删除数量结束点
            int endNumber = Integer.valueOf(editText_endNumber.getText().toString());
            //删除数量为0
            if (endNumber == 0){
                Toast.makeText(this,"请输入删除关卡的数量!",Toast.LENGTH_SHORT).show();
            }
            //删除数量不为0
            else {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                for (int i = startNumber; i <= endNumber ; i++) {
                    database.delete("tb_game_map","level = ?",new String[]{String.valueOf(i)});
                }
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
