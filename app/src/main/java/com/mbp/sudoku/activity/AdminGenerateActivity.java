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
 * 管理员生成数据库活动类
 */
public class AdminGenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_layout);
        //生成地图按钮
        Button button1 = findViewById(R.id.btn_generate);
        //关卡数量输入框
        EditText editText1 = findViewById(R.id.level_number);

        button1.setOnClickListener(view -> {
            //关卡数量
            int levelNumber = Integer.valueOf(editText1.getText().toString());
            //输入数量为空
            if (levelNumber == 0){
                Toast.makeText(this,"请输入关卡数量!",Toast.LENGTH_SHORT).show();
            }
            //输入数量不为空
            else {
                //生成游戏地图
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
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
                    //第一关默认解锁状态
                    if (i == 0){
                        values.put("status",0);
                    }
                    database.insert("tb_game_map", null, values);
                }

                //初始化游戏最后一次保存进度
                ContentValues values = new ContentValues();
                values.put("level", 0);
                database.insert("tb_end_speed", null, values);

                Toast.makeText(AdminGenerateActivity.this,"生成地图成功!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
