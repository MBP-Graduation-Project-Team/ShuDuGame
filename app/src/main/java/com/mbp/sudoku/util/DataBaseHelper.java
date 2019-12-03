package com.mbp.sudoku.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


/**数据库帮助类**/
public class DataBaseHelper extends SQLiteOpenHelper {

    /** 上下文 **/
    private Context dbContext;

    /** 建表语句 **/
    private static final String CREATE__GAME_MAP = "Create table tb_game_map(" +
            "level integer primary key autoincrement NOT NULL," +
            "original_map text NOT NULL," +
            "game_map text NOT NULL," +
            "status integer NOT NULL DEFAULT -1," +
            "good_time integer NOT NULL DEFAULT 0" +
            ")";

    /** 建表语句 **/
    private static final String CREATE__GAME_SPEED = "Create table tb_game_speed(" +
            "level integer primary key NOT NULL," +
            "game_speed text," +
            "now_time integer," +
            "error_number integer NOT NULL DEFAULT 0" +
            ")";

    /** 建表语句 **/
    private static final String CREATE__GAME_END_SPEED = "Create table tb_end_speed (" +
            "level integer NOT NULL DEFAULT 0" +
            ")";

    /**
     * 构造方法
     * @param context 上下文
     * @param name 参数
     * @param factory 参数
     * @param version 版本号
     */
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dbContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE__GAME_MAP);
        db.execSQL(CREATE__GAME_SPEED);
        db.execSQL(CREATE__GAME_END_SPEED);
        Toast.makeText(dbContext,"创建数据库成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onCreate(db);
    }
}
