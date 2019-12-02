package com.mbp.sudoku.util;

import java.util.Locale;

public class TimeUtil {
    /**
     * 格式化时间
     * @param cnt 计时器
     * @return 时间
     */
    public String getStringTime(int cnt) {
        int hour = cnt/3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
    }
}
