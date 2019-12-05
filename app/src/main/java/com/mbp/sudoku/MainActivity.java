package com.mbp.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mbp.sudoku.activity.CheckPointActivity;
import com.mbp.sudoku.activity.GameActivity;
import com.mbp.sudoku.util.DatabaseUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 主页面活动类
 */
public class MainActivity extends AppCompatActivity {

    /** 最后一次游戏关卡 **/
    private int level = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initDatabase();

        //获取最后一次游戏关卡
        SQLiteDatabase database = DatabaseUtil.getDatabase(this);
        Cursor cursor = database.query("tb_end_speed",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            level = cursor.getInt(0);
            Log.d("获取最后一次游戏关卡",String.valueOf(level));
        }
        cursor.close();
        database.close();

        //继续游戏按钮
        Button btn_continue = findViewById(R.id.game_continue);
        //开始游戏按钮
        Button btn_start = findViewById(R.id.game_begin);
        //隐藏继续游戏按钮
        if (level == 0){
            btn_continue.setVisibility(View.INVISIBLE);
        }

        //开始游戏按钮监听器
        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CheckPointActivity.class);
            startActivity(intent);
        });

        //继续游戏按钮监听器
        btn_continue.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("gameType","continue");
            intent.putExtra("level",level);
            startActivity(intent);
        });
    }

    /**
     * 初始化数据库
     */
    void initDatabase(){
        // databases 目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
        // 数据库名为 ShuDu.db
        @SuppressLint("SdCardPath") final String DB_PATH = "/data/data/com.mbp.sudoku/databases/";
        final String DB_NAME = "ShuDu.db";

        // 检查 SQLite 数据库文件是否存在
        if (!(new File(DB_PATH + DB_NAME)).exists()) {
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
    }
}
