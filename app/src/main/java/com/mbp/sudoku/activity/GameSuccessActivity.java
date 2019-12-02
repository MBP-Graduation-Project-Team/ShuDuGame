package com.mbp.sudoku.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.MainActivity;
import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.TimeUtil;

public class GameSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_layout);

        Intent intent = getIntent();
        int level = intent.getIntExtra("level",0);
        int time = intent.getIntExtra("time",0);

        TextView tv_title = findViewById(R.id.checkpoint_number);
        tv_title.setText("第" + level + "关");
        TextView tv_time = findViewById(R.id.the_time);
        TimeUtil timeUtil = new TimeUtil();
        tv_time.setText(timeUtil.getStringTime(time));
        TextView tv_bastTime = findViewById(R.id.best_time);
        TextView tv_over = findViewById(R.id.over_text);

        //获取最佳通关时间
        int goodTime;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select good_time from tb_game_map where level = ?",new String[]{String.valueOf(level)});
        if (cursor.moveToFirst()){
            goodTime = cursor.getInt(0);
            tv_bastTime.setText(timeUtil.getStringTime(goodTime));
            Log.i("goodTime", String.valueOf(goodTime));
        }
        cursor.close();

        //获取总关卡数
        Cursor cursor2 = database.query("tb_game_map",null,null,null,null,null,null);
        int levelCount = cursor2.getCount();
        if (level < levelCount){
            tv_over.setVisibility(View.INVISIBLE);
        }
        cursor2.close();

        Button btn_next = findViewById(R.id.next_point);
        btn_next.setOnClickListener(view -> {
            Intent intent1 = new Intent(GameSuccessActivity.this,GameActivity.class);
            intent1.putExtra("level",level + 1);
            intent1.putExtra("gameType","new");
            startActivity(intent1);
        });
        Button btn_back = findViewById(R.id.return_main);
        btn_back.setOnClickListener(view -> {
            Intent intent2 = new Intent(GameSuccessActivity.this, MainActivity.class);
            startActivity(intent2);

        });

        //更新数据
        ContentValues values = new ContentValues();
        values.put("level", 0);
        database.update("tb_end_speed", values,null, null);
    }
}
