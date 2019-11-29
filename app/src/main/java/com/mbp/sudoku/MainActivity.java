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

import com.mbp.sudoku.activity.CheckPointActivity;
import com.mbp.sudoku.activity.GameActivity;
import com.mbp.sudoku.test.TestU;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.GenerateUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestU tu=new TestU();
        int a[][]=tu.getMap();
        System.out.println("地图1"+Arrays.deepToString(a));
        GenerateUtil gu=new GenerateUtil();
        gu.maskCells(a);
        String aa=Arrays.deepToString(gu.maskCells(a));
        System.out.println("地图2"+Arrays.deepToString(gu.maskCells(a)));
        setContentView(R.layout.main_layout);
      //  initDatabase();
//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"sudoku.db",null,2);
//        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        dbHelper=new DataBaseHelper(this,"new.db",null,2);
        SQLiteDatabase database =dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();

        values.put("gameMap",aa);
        database.insert("gamemap",null,values);
        values.clear();

       // initDatabase();
//        System.out.println(Arrays.deepToString(gu.maskCells(a)));
        /*Cursor cursor = database.query("gamemap",null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                String map = cursor.getString(1);
                Log.i("map", map);
            }while (cursor.moveToNext());
        }
        cursor.close();*/
        //继续游戏按钮
        Button btn_continue = findViewById(R.id.game_continue);
        //开始游戏按钮
        Button btn_start = findViewById(R.id.game_begin);
        //关卡编号
        /*if (id == 0){
            btn_continue.setVisibility(View.INVISIBLE);
        }*/

        //开始游戏按钮监听器
        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckPointActivity.class);
            startActivity(intent);
        });

        //继续游戏按钮监听器
        btn_continue.setOnClickListener(v -> {
            new Thread(() -> {

            }).start();
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });


//        dataBaseHelper.getWritableDatabase();

        /*for (int i = 0; i < 4; i++) {
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

//        database.delete("gamemap",null,null);



    }

    void initDatabase(){
        // com.test.db 是程序的包名，请根据自己的程序调整
        // /data/data/com.test.db/
        // databases 目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
        // 数据库名为 test.db
        String DB_PATH = "/data/data/com.mbp.sudoku/databases/";
        String DB_NAME = "new.db";

        // 检查 SQLite 数据库文件是否存在
        if ((new File(DB_PATH + DB_NAME)).exists() == false) {
            // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
            File f = new File(DB_PATH);
            // 如 database 目录不存在，新建该目录
            if (!f.exists()) {
                f.mkdir();
            }

            try {
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                // 输出流
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                // 关闭文件流
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 下面测试 /data/data/com.test.db/databases/ 下的数据库是否能正常工作
       /* SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = database.rawQuery("select * from gamemap", null);

        if (cursor.moveToFirst()){
            do {
                String map = cursor.getString(1);
                Log.i("map", map);
            }while (cursor.moveToNext());
        }
        cursor.close();*/
    }
}
