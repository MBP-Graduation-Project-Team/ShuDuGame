package com.mbp.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.MainActivity;
import com.mbp.sudoku.R;

public class SuspendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suspend_layout);
        Intent intent = getIntent();
        //关卡编号
        int level = intent.getIntExtra("level",1);

        //继续游戏
        Button btn_1 = findViewById(R.id.game_continue);
        btn_1.setOnClickListener(view -> {
            Intent intent1 = new Intent(SuspendActivity.this,GameActivity.class);
            intent1.putExtra("gameType","continue");
            intent1.putExtra("level",level);
            startActivity(intent1);
        });

        //选择关卡
        Button btn_2 = findViewById(R.id.game_select);
        btn_2.setOnClickListener(view -> {
            Intent intent1 = new Intent(SuspendActivity.this,CheckPointActivity.class);
            startActivity(intent1);
        });

        //重新开始
        Button btn_3 = findViewById(R.id.game_restart);
        btn_3.setOnClickListener(view -> {
            Intent intent1 = new Intent(SuspendActivity.this,GameActivity.class);
            intent1.putExtra("gameType","new");
            intent1.putExtra("level",level);
            startActivity(intent1);
        });

        //返回主页
        Button btn_4 = findViewById(R.id.return_main);
        btn_4.setOnClickListener(view -> {
            Intent intent1 = new Intent(SuspendActivity.this, MainActivity.class);
            startActivity(intent1);
        });
    }
}
