package com.mbp.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        //生成关卡按钮
        Button button1 = findViewById(R.id.btn_generate);
        //操作关卡按钮
        Button button2 = findViewById(R.id.btn_operate);
        //查看关卡按钮
        Button button3 = findViewById(R.id.btn_select);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this,AdminGenerateActivity.class);
            startActivity(intent);
        });
        button2.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this,AdminOperateActivity.class);
            startActivity(intent);
        });
        button3.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this,AdminSelectActivity.class);
            startActivity(intent);
        });
    }
}
