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
import com.mbp.sudoku.util.MapUtil;
import com.mbp.sudoku.util.TimeUtil;

public class GameSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        //关卡编号
        int level = intent.getIntExtra("level",1);
        Log.d("GameView","删除当前关卡记录");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(GameSuccessActivity.this,"ShuDu.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        database.delete("tb_game_speed","level = ?",new String[]{String.valueOf(level)});

        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_layout);

        //当前通关时间
        int time = intent.getIntExtra("time",0);

        TextView tv_title = findViewById(R.id.checkpoint_number);
        tv_title.setText("第" + level + "关");
        TextView tv_time = findViewById(R.id.the_time);
        TimeUtil timeUtil = new TimeUtil();
        tv_time.setText(timeUtil.getStringTime(time));
        TextView tv_bastTime = findViewById(R.id.best_time);
        TextView tv_over = findViewById(R.id.over_text);

        //获取最佳通关时间
        Cursor cursor = database.rawQuery("select good_time from tb_game_map where level = ?",new String[]{String.valueOf(level)});
        if (cursor.moveToFirst()){
            int goodTime = cursor.getInt(0);
            tv_bastTime.setText(timeUtil.getStringTime(goodTime));
            Log.i("goodTime", String.valueOf(goodTime));
        }
        cursor.close();

        //获取总关卡数
        Cursor cursor2 = database.query("tb_game_map",null,null,null,null,null,null);
        int levelCount = cursor2.getCount();
        //未全部通关
        if (level < levelCount){
            tv_over.setVisibility(View.INVISIBLE);
            //解锁下一关
            ContentValues values1 = new ContentValues();
            values1.put("status", 0);
            database.update("tb_game_map", values1,"level = ?", new String[]{String.valueOf(level + 1)});
        }
        cursor2.close();

        //下一关按钮
        Button btn_next = findViewById(R.id.next_point);
        btn_next.setOnClickListener(view -> {
            Intent intent1 = new Intent(GameSuccessActivity.this,GameActivity.class);
            intent1.putExtra("level",level + 1);
            intent1.putExtra("gameType","new");
            startActivity(intent1);
        });

        //返回主页面按钮
        Button btn_back = findViewById(R.id.return_main);
        btn_back.setOnClickListener(view -> {
            Intent intent2 = new Intent(GameSuccessActivity.this, MainActivity.class);
            startActivity(intent2);

        });

        //更新最后一次游戏进度
        ContentValues values = new ContentValues();
        values.put("level", 0);
        database.update("tb_end_speed", values,null, null);
    }
}
