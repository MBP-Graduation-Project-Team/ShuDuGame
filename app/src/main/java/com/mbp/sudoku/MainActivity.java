package com.mbp.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.view.GameView;

public class MainActivity extends AppCompatActivity {


    private GameView gV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gV =  findViewById(R.id.game);
    }

    public void rePay(View v){
        gV.repeat();
    }
    public void newPay(View v){
        gV.play();

    }
}
