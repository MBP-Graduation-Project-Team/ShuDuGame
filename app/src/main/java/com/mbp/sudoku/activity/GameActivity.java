package com.mbp.sudoku.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.MapUtil;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    /** **/
    private TimerTask timerTask;
    /** **/
    private Timer timer = new Timer();
    /** 计时器 **/
    private TextView timeShow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent gameIntent = getIntent();
        String gameType = gameIntent.getStringExtra("");
        getGameMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        timeShow = findViewById(R.id.game_time);
        timerTask = new TimerTask() {
            int cnt = 0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeShow.setText(getStringTime(cnt++));
                    }
                });
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    /**
     * 格式化时间
     * @param cnt 计时器
     * @return 时间
     */
    private String getStringTime(int cnt) {
        int hour = cnt/3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
    }

    /**
     * 暂停计时器
     */
    private void stopTime(){
        if (!timerTask.cancel()){
            timerTask.cancel();
            timer.cancel();
        }
    }

    /**
     * JSON转int二维数组
     * @param inputString json
     * @return 二维数组
     */
    private static int[][] StringToArray(String inputString){
        int[][] array = new int[9][9];
        String newString = inputString;
        newString = newString.replaceAll("\\[","");
        newString = newString.replaceAll("]","");
        newString = newString.replaceAll(",","");
        int n = 0;
        for (int i = 0;i < 9;i++){
            for (int j = 0; j < 9; j++) {
                array[i][j] = newString.charAt(n) - 48;
                n++;
            }
        }
        return  array;
    }

    /**
     * 获取游戏地图
     */
    public void getGameMap(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"sudoku.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from gamemap where id = ?",new String[]{"12"});
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String gameMap = cursor.getString(1);
                int [][]firstMap = StringToArray(gameMap);
                String mapStatus = cursor.getString(2);
                int[][]gameMapArray = StringToArray(mapStatus);
                MapUtil mapUtil = new MapUtil(gameMapArray,firstMap);
                String goodTime = cursor.getString(3);
                int status = cursor.getInt(4);
                Log.i("id", String.valueOf(id));
                Log.i("gameMap", gameMap);
                Log.i("mapStatus", mapStatus);
                Log.i("goodTime", goodTime);
                Log.i("status", String.valueOf(status));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

}
