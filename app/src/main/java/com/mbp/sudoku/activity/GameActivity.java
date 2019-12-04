package com.mbp.sudoku.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mbp.sudoku.R;
import com.mbp.sudoku.util.DataBaseHelper;
import com.mbp.sudoku.util.MapUtil;
import com.mbp.sudoku.util.TimeUtil;
import com.mbp.sudoku.view.GameView;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    /** TimerTask **/
    private TimerTask timerTask;
    /** 时间 **/
    private Timer timer = new Timer();
    /** 计时器显示 **/
    private TextView timeShow;
    /** 关卡编号 **/
    private int level;
    /** 耗时 **/
    private int cnt = MapUtil.getTime();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent gameIntent = getIntent();
        String gameType = gameIntent.getStringExtra("gameType");
        level = gameIntent.getIntExtra("level",1);
        updateEndLevelSpeed(level);
        getGameMap(level);
        //继续游戏
        if ("continue".equals(gameType)){
            Log.d("GameActivity","继续游戏!");
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from tb_game_speed where level = ?",new String[]{String.valueOf(level)});
            if (cursor.moveToFirst()){
                do {
                    String currentMap = cursor.getString(1);
                    cnt = cursor.getInt(2);
                    int errorCount = cursor.getInt(3);
                    MapUtil.setCutData(StringToArray(currentMap));
                    GameView.setErrorCount(errorCount);
                    Log.d("当前游戏地图", Arrays.deepToString(StringToArray(currentMap)));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        //新游戏
        if ("new".equals(gameType)){
            Log.d("GameActivity","新游戏!");
            //重置错误数量
            GameView.setErrorCount(0);
            //重置计时器时间
            MapUtil.setTime(0);
            cnt = 0;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        //计时器
        timeShow = findViewById(R.id.game_time);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                MapUtil.setTime(cnt);
                TimeUtil timeUtil = new TimeUtil();
                runOnUiThread(() -> timeShow.setText(timeUtil.getStringTime(cnt++)));
            }
        };
        timer.schedule(timerTask,0,1000);

        //暂停按钮
        Button btn_suspend = findViewById(R.id.return_button);
        btn_suspend.setOnClickListener(view -> {
            Intent intent = new Intent(GameActivity.this,SuspendActivity.class);
            intent.putExtra("level",level);
            startActivity(intent);
        });
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
     * @param level 关卡编号
     */
    public void getGameMap(int level){
        Log.d("GameActivity","获取游戏地图");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from tb_game_map where level = ?",new String[]{String.valueOf(level)});
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String gameMap = cursor.getString(1);
                int [][]firstMap = StringToArray(gameMap);
                String mapStatus = cursor.getString(2);
                int[][]gameMapArray = StringToArray(mapStatus);
                MapUtil mapUtil = new MapUtil(gameMapArray,firstMap,id);
                int status = cursor.getInt(3);
                String goodTime = cursor.getString(4);
                Log.i("id", String.valueOf(id));
                Log.i("gameMap", gameMap);
                Log.i("mapStatus", mapStatus);
                Log.i("goodTime", goodTime);
                Log.i("status", String.valueOf(status));
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 标记最后一次游戏进度
     * @param level 关卡编号
     */
    private void updateEndLevelSpeed(int level){
        Log.d("GameActivity","标记最后一次游戏进度");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this,"ShuDu.db",null,1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        //更新数据
        ContentValues values = new ContentValues();
        values.put("level", level);
        database.update("tb_end_speed", values,null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("GameView", "保存游戏进度");

        Gson gson = new Gson();
        String mapJson = gson.toJson(MapUtil.getCutData());

        //获取游戏进度
        DataBaseHelper dataBaseHelper = new DataBaseHelper(GameActivity.this, "ShuDu.db", null, 1);
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        Cursor cursor1 = database.rawQuery("select game_speed from tb_game_speed where level = ?", new String[]{String.valueOf(level)});
        //不存在游戏进度
        if (cursor1.getCount() == 0) {
            Log.d("", "不存在游戏进度");
            ContentValues values = new ContentValues();
            values.put("level", level);
            values.put("game_speed", mapJson);
            values.put("now_time", cnt);
            values.put("error_number", GameView.getErrorCount());
            database.insert("tb_game_speed", null, values);
        }
        //存在游戏进度
        else {
            Log.d("", "存在游戏进度");
            //更新数据
            ContentValues values = new ContentValues();
            values.put("game_speed", mapJson);
            values.put("now_time", cnt);
            values.put("error_number", GameView.getErrorCount());
            database.update("tb_game_speed", values, "level = ?", new String[]{String.valueOf(level)});
        }
        cursor1.close();
    }
}
