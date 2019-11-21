package com.mbp.sudoku.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.Random;

/**
 * @author 邓宁
 * @date Created in 15:25 2019/11/13
 */
public class A {
    private static final int SIZE = 9;
    private static final int CELL_SIZE = 3;
    private static final int LEVEL_MAX = 10;
    private static int[][] suduAry = new int[SIZE][SIZE];

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
    private static void saveDataToFile(String fileName,String data) {
        BufferedWriter writer = null;
        File file = new File("d:\\" + fileName + ".json");
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
            /*GameMap gameMap = new GameMap();
            gameMap.setId(1);
            gameMap.setGameMap(generate(1));
            gameMap.setMapStatus(generate(1));
            gameMap.setSpeedStatus(0);
            gameMap.setStatus(0);
            System.out.println(JSON.toJSONString(gameMap));*/
            //saveDataToFile("game_map",JSON.toJSONString(gameMap));
            /*System.out.println(readJsonFile("d:\\" + "game_map" + ".json"));
            ObjectMapper objectMapper = new ObjectMapper();
            GameMap gameMap = objectMapper.readValue(readJsonFile("d:\\" + "game_map" + ".json"),GameMap.class);
            System.out.println(Arrays.deepToString(gameMap.getGameMap()));
            System.out.println(gameMap.getId());
            System.out.println(Arrays.deepToString(gameMap.getMapStatus()));
            System.out.println(gameMap.getSpeedStatus());
            System.out.println(gameMap.getStatus());*/
//            System.out.println(JSON.toJSONString(generate(1)));
            System.out.println(Arrays.deepToString(generate(1)));
    }

    private static int floorDiv(int x, int y) {
        int r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    /**
     * 生成数独
     * @param level 难度级别 1~10
     * @return int[][]
     */
    public static int[][] generate(int level) {
        Random random = new Random();
        int n = random.nextInt(9) + 1;
        for(int i=0;i<SIZE;i++) {
            for(int j=0;j<SIZE;j++) {
                int p = floorDiv(i, CELL_SIZE);
                int q = floorDiv(j, CELL_SIZE);
                for(int k=0;k<SIZE;k++) {
                    if(checkColumn(n, j) && checkRow(n, i) && checkNineCells(n, p, q)) {
                        suduAry[i][j] = n;
                        break;
                    } else {
                        n = n % SIZE + 1;
                    }
                }
            }
            n = n % SIZE + 1;
        }
        upset();
        maskCells(level);

        return suduAry;
    }

    private static void maskCells(int level) {
        int min, max;
        level %= LEVEL_MAX;
        if(level == 0) level = LEVEL_MAX;

        if(level <4) {
            min = 20;
            max = 15;
        } else if(level < 7) {
            min = 40;
            max = 10;
        } else if(level < 10) {
            min = 50;
            max = 10;
        } else {
            min = 60;
            max = 5;
        }

        Random random = new Random();
        int count = random.nextInt(max) + min;
        for(int i=0;i<count;i++) {
            do {
                int n = random.nextInt(SIZE);
                int m = random.nextInt(SIZE);
                if(suduAry[n][m] > 0) {
                    suduAry[n][m] = 0;
                    break;
                }
            }while(true);
        }
    }

    /**
     * 随机打乱顺序
     */
    private static void upset() {
        Random random = new Random();
        //按行交换
        for(int i=0;i<SIZE;i++) {
            int n = random.nextInt(CELL_SIZE);
            int p = random.nextInt(CELL_SIZE) * CELL_SIZE + n;
            for(int j=0;j<SIZE;j++) {
                int tmp = suduAry[i][j];
                suduAry[i][j] = suduAry[p][j];
                suduAry[p][j] = tmp;
            }
        }
        //按列交换
        for(int i=0;i<SIZE;i++) {
            int n = random.nextInt(CELL_SIZE);
            int p = random.nextInt(CELL_SIZE) * CELL_SIZE + n;
            for(int j=0;j<SIZE;j++) {
                int tmp = suduAry[j][i];
                suduAry[j][i] = suduAry[j][p];
                suduAry[j][p] = tmp;
            }
        }
    }

    /**
     * 检查某行
     * @param n
     * @param row
     * @return
     */
    private static boolean checkRow(int n, int row) {
        boolean result = true;

        for(int i=0;i<SIZE;i++) {
            if(suduAry[row][i] == n) {
                result = false;
                break;
            }
        }

        return result;
    }
    /**
     * 检查某列
     * @param n
     * @param col
     * @return
     */
    private static boolean checkColumn(int n, int col) {
        boolean result = true;
        for(int i=0;i<SIZE;i++) {
            if(suduAry[i][col] == n) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 检查小九宫格
     * @param n
     * @param x
     * @param y
     * @return
     */
    private static boolean checkNineCells(int n, int x, int y) {
        boolean result = true;
        int sx = x * 3, sy = y * 3;

        for(int i=sx; i<sx+3;i++) {
            for(int j=sy;j<sy+3;j++) {
                if(suduAry[i][j] == n) {
                    result = false;
                    break;
                }
            }
            if(!result) break;
        }

        return result;
    }

}
