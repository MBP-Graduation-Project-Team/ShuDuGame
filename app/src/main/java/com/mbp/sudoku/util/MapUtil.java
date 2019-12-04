package com.mbp.sudoku.util;


public class MapUtil {

    /** 原始地图 **/
    private static int[][] firstMap;
    /** 游戏地图 **/
    private static int[][] gameMap;
    /** 当前地图 **/
    private static int[][] cutData;
    /** 当前关卡编号 **/
    private static int level;
    /** 耗时 **/
    private static int time;

    /**
     * 构造方法
     * @param gameMap 游戏地图
     * @param firstMap 原始地图
     */
    public MapUtil(int[][] gameMap,int[][] firstMap,int id) {
        MapUtil.firstMap = firstMap;
        MapUtil.gameMap = gameMap;
        MapUtil.level = id;
        initCutData();
    }

    public MapUtil() { }

    /** 得到当前坐标上的文字 */
    public String getText(int x, int y){
        String index = String.valueOf(gameMap[x][y]);
        if ("0".equals(index)) {
            index = "";
        }
        return index;
    }

    /** 判断该坐标是否可以点击 */
    public boolean getOnClicked(int x, int y){
        return gameMap[x][y] == 0;
    }

    /** 判断该坐标有哪些数不可用 */
    /*public int[] getFalseData(int x, int y){
        Set<Integer> set = new TreeSet<>();
        // 检查X 轴有哪些不能点
        for (int i = 0; i < 9; i++) {
            int d = gameMap[y][i];
            if (d!=0) {
                set.add(d);
            }
        }
        // 检查 y 轴有哪些不能点
        for (int i = 0; i < 9; i++) {
            int d = gameMap[i][x];
            if (d!=0) {
                set.add(d);
            }
        }
        // 检查 3*3 方格哪些不能点
        x = x/3*3;
        y = y/3*3;
        for (int i = x; i < x+3; i++) {
            for (int j = y; j < y+3; j++) {
                int d = gameMap[j][i];
                if (d!=0) {
                    set.add(d);
                }
            }
        }
        Integer[] arr2 = set.toArray(new Integer[0]);
        // 数组的包装类型不能转 只能自己转；吧Integer转为为int数组；
        int[] result = new int[arr2.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr2[i];
        }
        System.out.println("false Number ： "+ Arrays.toString(result));
        return result;
    }*/

    /** 当前棋盘数据 */
    public void initCutData(){
        cutData = new int[9][9];
        for (int i = 0; i < gameMap.length; i++) {
            System.arraycopy(gameMap[i], 0, cutData[i], 0, gameMap[i].length);
        }
    }

    /**
     * 填入数字
     * @param x 横坐标
     * @param y 纵坐标
     * @param data 数字
     */
    public void setCutData(int x, int y, int data){
        if (getOnClicked(x, y)) {
            cutData[x][y] = data;
        }
    }

    /**
     * 获取当前坐标数字
     * @param x 横坐标
     * @param y 纵坐标
     * @return 当前坐标数字
     */
    public int getCutData(int x, int y){
        return cutData[x][y];
    }

    /**
     * 判断填入数字是否正确
     * @param x 横坐标
     * @param y 纵坐标
     * @param data 填入数字
     * @return 判断结果
     */
    public boolean judgeNumber(int x, int y, int data){
        return firstMap[x][y] == data;
    }

    /**
     * 判断是否全部完成
     * @return 判断结果
     */
    public boolean isSuccess(){
        boolean result = true;
        for (int i = 0; i < 9;i++){
            for (int j = 0;j < 9;j++){
                if (cutData[i][j] == 0){
                    result = false;
                }
            }
        }
        return  result;
    }


    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        MapUtil.level = level;
    }

    public static int[][] getCutData() {
        return cutData;
    }

    public static void setCutData(int[][] cutData) {
        MapUtil.cutData = cutData;
    }

    public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        MapUtil.time = time;
    }
}
