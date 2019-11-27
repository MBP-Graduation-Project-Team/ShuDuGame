package com.mbp.sudoku.util;



import com.google.gson.Gson;
import com.mbp.sudoku.entity.GameMapEntity;

import java.io.*;
import java.util.Arrays;

/**
 * @author 邓宁
 * @date Created in 15:25 2019/11/13
 */
public class JsonUtil {


    /**
     * 读取json文件，返回json串
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成JSON文件
     * @param fileName 文件名
     * @param data 写入数据
     */
    public static void saveDataToFile(String path,String fileName,String data) {
        BufferedWriter writer = null;
        File file = new File(path + fileName + ".json");
        //如果文件不存在，则新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");

    }
    public static void main(String[] args) throws IOException {


        /*GameMapEntity asd = gson.fromJson(jsonStr,GameMapEntity.class);
        int[][]array = asd.getGameMap();
        System.out.println(Arrays.deepToString(array));*/


    }
}
