package com.mbp.sudoku.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;

public class AdminGenerateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        Button button1 = findViewById(R.id.btn_generate);
        EditText editText1 = findViewById(R.id.level_number);
        button1.setOnClickListener(view -> {
            int levelNumber = Integer.valueOf(editText1.getText().toString());
            if (levelNumber == 0){
                Toast.makeText(this,"请输入关卡数量!",Toast.LENGTH_SHORT).show();
            }
            else {
                //创建数据库
                DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"test.db",null,1);
                SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
                //插入数据
                /*for (int i = 0; i < levelNumber; i++) {
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

                Cursor cursor = database.query("gamemap",null,null,null,null,null,null);

                if (cursor.moveToFirst()){
                    do {
                        int id = cursor.getInt(0);
                        String map = cursor.getString(1);
                        Log.i("id", String.valueOf(id));
                        Log.i("map", map);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

    }
}
