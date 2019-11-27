package com.mbp.sudoku.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String requestGet(String requestUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置为GET请求
            urlConn.setRequestMethod("GET");
            InputStream inputStream = urlConn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            Log.d("返回结果",stringBuilder.toString());

        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return stringBuilder.toString();
    }
}
