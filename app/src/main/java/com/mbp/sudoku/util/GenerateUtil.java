package com.mbp.sudoku.util;


import com.google.gson.Gson;
import com.mbp.sudoku.entity.GameMapEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author 邓宁
 * @deprecated  Created in 15:25 2019/11/13
 */
public class GenerateUtil {
    private static final int SIZE = 9;
//    private static final int CELL_SIZE = 3;
    private static final int LEVEL_MAX = 10;
//    private int[][] suduAry = new int[SIZE][SIZE];

    //数独地图数组
    private static int[][] maps = new int[9][9];
    //每个小九宫格可放置位置的数
    private static int[] canPutSum = new int[9];
    //用来存储之前放置过的位置
    private static int[] used = new int[9];
    //是否已经完成地图的生成
    private static boolean isOk = true;


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
//    public GameMapEntity generate(int level) {
//        Random random = new Random();
//        int n = random.nextInt(9) + 1;
//        for(int i=0;i<SIZE;i++) {
//            for(int j=0;j<SIZE;j++) {
//                int p = floorDiv(i, CELL_SIZE);
//                int q = floorDiv(j, CELL_SIZE);
//                for(int k=0;k<SIZE;k++) {
//                    if(checkColumn(n, j) && checkRow(n, i) && checkNineCells(n, p, q)) {
//                        suduAry[i][j] = n;
//                        break;
//                    } else {
//                        n = n % SIZE + 1;
//                    }
//                }
//            }
//            n = n % SIZE + 1;
//        }
//        upset();
//        GameMapEntity gameMap = new GameMapEntity();
//        maskCells(suduAry);
//        return gameMap;
//    }
    /**
     * 生成数独
     * @return int[][]
     */
//    public int[][] generate() {
//        Random random = new Random();
//        int n = random.nextInt(9) + 1;
//        for(int i=0;i<SIZE;i++) {
//            for(int j=0;j<SIZE;j++) {
//                int p = floorDiv(i, CELL_SIZE);
//                int q = floorDiv(j, CELL_SIZE);
//                for(int k=0;k<SIZE;k++) {
//                    if(checkColumn(n, j) && checkRow(n, i) && checkNineCells(n, p, q)) {
//                        suduAry[i][j] = n;
//                        break;
//                    } else {
//                        n = n % SIZE + 1;
//                    }
//                }
//            }
//            n = n % SIZE + 1;
//        }
//        upset();
//        return suduAry;
//    }

    public int[][] maskCells(int[][] suduAry) {
        int level = 1;
        int min, max;
        level %= LEVEL_MAX;
        if(level == 0) level = LEVEL_MAX;

        if(level < 4) {
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
        return suduAry;
    }

    /**
     * 随机打乱顺序
     *//*
    private void upset() {
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

    *//**
     * 检查某行
     * @param n
     * @param row
     * @return
     *//*
    private  boolean checkRow(int n, int row) {
        boolean result = true;

        for(int i=0;i<SIZE;i++) {
            if(suduAry[row][i] == n) {
                result = false;
                break;
            }
        }

        return result;
    }
    *//**
     * 检查某列
     * @param n
     * @param col
     * @return
     *//*
    private boolean checkColumn(int n, int col) {
        boolean result = true;
        for(int i=0;i<SIZE;i++) {
            if(suduAry[i][col] == n) {
                result = false;
                break;
            }
        }
        return result;
    }

    *//**
     * 检查小九宫格
     * @param n
     * @param x
     * @param y
     * @return
     *//*
    private boolean checkNineCells(int n, int x, int y) {
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
    }*/


    /*
     * 得到数独地图数组
     */
    public static int[][] getMap() {
        //判断是否已经完成地图的生成，要是没有完成就重新生成。
        //从这里就可以看出算法还有待优化，如果回溯的好的话就一直可以通过回溯来重新生成，而这里是通过重新执行生成算法来重新生成。希望感兴趣的朋友可以去实现以下。
        do{
            isOk = true;
            initMaps();
        }while(!isOk);
        return maps;
    }

    /*
     * 初始化maps
     */
    private static void initMaps() {
        // 初始化地图数组中没有填入任何数字
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                maps[i][j] = -1;
            }
        }

        // 依次填入1~9
        for (int num = 1; num <= 9; num++) {
            for (int i = 0; i < 9; i++) {
                used[i] = -1;
                canPutSum[i] = -1;
            }
            // 遍历大九宫格中的每个小九宫格
            for (int i = 0; i < 9; i++) {
                if (canPutSum[i]==-1) {
                    canPutSum[i] = getCanPutSum(i, num);
                }
                if (canPutSum[i]==1) {
                    used[i] = -1;
                }

                if (canPutSum[i] == 0) {
                    canPutSum[i] = -1;
                    used[i] = -1;
                    // 如果当前小九宫格中不能放入数字num，则回到前一个小九宫格
                    if (i > 0) {
                        // 将前一个九宫格中放num的位置清空
                        if (used[i-1]!=-1) {
                            //maps[(int) (Math.floor(used[i-1]/3)+Math.floor((i-1)/3)*3)][used[i-1]%3+((i-1)%3)*3]=-1;
                            clearNum(i - 1, num);
                        }
                        // i回退一个，因为等会for循环灰给i加一，所以这里减2
                        i -= 2;
                        continue;
                    } else {
                        isOk = false;
                        return;
                    }
                } else {
                    // 将num放入当前小九宫格中
                    boolean flag = false;
                    while (!flag) {
                        int j = (int) (Math.random() * 9);
                        // 当前小方格横坐标
                        int ii = (i / 3) * 3 + j / 3;
                        // 当前小方格纵坐标
                        int jj = (i % 3) * 3 + j % 3;
                        //System.out.println("num:"+num+"\tii:"+ii+"\tjj:"+jj);
                        // 如果可以放置num则放置
                        if (maps[ii][jj] == -1 && j!=used[i] && isCanPut(ii, jj, num)) {
                            maps[ii][jj] = num;
                            used[i] = j;
                            canPutSum[i] -= 1;
                            flag = true;
                        }

                    }
                }

            }
        }

    }

    /*
     * 清空第i个小九宫格中的num
     */
    private static void clearNum(int i, int num) {
        for (int j = 0; j < 9; j++) {
            // 当前小方格横坐标
            int ii = (i / 3) * 3 + j / 3;
            // 当前小方格纵坐标
            int jj = (i % 3) * 3 + j % 3;
            // 判断当前小方格是否可以放置
            if (maps[ii][jj] == num) {
                maps[ii][jj] = -1;
            }
        }

    }

    /*
     * 得到当前小九宫格可以放入数字num的位置数目
     */
    private static int getCanPutSum(int i, int num) {
        int sum = 0;
        // 遍历小九宫格
        for (int j = 0; j < 9; j++) {
            // 当前小方格横坐标
            int ii = (i / 3) * 3 + j / 3;
            // 当前小方格纵坐标
            int jj = i % 3 * 3 + j % 3;
            // 判断当前小方格是否可以放置
            if (maps[ii][jj] == -1 && isCanPut(ii, jj, num)) {
                ++sum;
            }
        }

        return sum;

    }

    /*
     * 指定横纵坐标点是否可以放置num
     */
    private static boolean isCanPut(int ii, int jj, int num) {
        // 判断指定坐标点的同行或同列是否有相同数字，要是有则为false
        for (int i = 0; i < 9; i++) {
            if (maps[ii][i] == num) {
                return false;
            }
            if (maps[i][jj] == num) {
                return false;
            }
        }
        return true;
    }

}
