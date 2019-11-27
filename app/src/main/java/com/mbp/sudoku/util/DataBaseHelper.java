package com.mbp.sudoku.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


/**数据库帮助类**/
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context DBcontext;

    private static final String CREATE__GAMEMAP = "Create table gamemap(" +
            "id integer primary key autoincrement NOT NULL," +
            "gameMap text NOT NULL," +
            "mapStatus text NOT NULL DEFAULT 0," +
            "goodTime text," +
            "status integer NOT NULL DEFAULT 0" +
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
        DBcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE__GAMEMAP);
        Toast.makeText(DBcontext,"create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
