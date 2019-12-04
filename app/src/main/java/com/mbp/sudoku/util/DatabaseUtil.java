package com.mbp.sudoku.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {

    public static SQLiteDatabase getDatabase(Context context){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context,"ShuDu.db",null,1);
        return dataBaseHelper.getWritableDatabase();
    }
}
