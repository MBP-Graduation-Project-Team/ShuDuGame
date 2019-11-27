package com.mbp.sudoku.util;

import android.content.Context;

public class DataBaseUtil {

    public static void select(Context context){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context,"sudoku.db",null,1);

    }
}
