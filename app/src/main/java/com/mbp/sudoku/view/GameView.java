package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mbp.sudoku.util.MapUtil;


/**
 * 游戏地图
 * 时间：11-20
 */
public class GameView extends View {

    /** 手机屏幕的宽度 **/
    private int phoneWidth;
    /** 当前格子的宽度 **/
    private int cellWidth;
    /** 白线 **/
    private Paint mLinePaint;
    /** 浅蓝色的方格子 **/
    private Paint mDarkPaint;
    /** 用户点击 浅绿色的格子 **/
    private Paint mOptDarkPaint;
    /** 原始数据 数字 **/
    private Paint numberPaint;
    /** 用户填写的数字 **/
    private Paint changePaint;
    /** 错误数字区 **/
    private Paint errorNumberPaint;
    /** 候选区数字 **/
    private Paint mOptPaint;
    /** 地图信息 **/
    private MapUtil gameMap = new MapUtil();
    /** 数字X轴偏移量 **/
    private float tCX;
    /** 数字Y轴偏移量 **/
    private int mOptBoard;
    /** 数字Y轴偏移量 **/
    private float tCY;

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context) {
        super(context);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //获取手机屏幕宽度
        phoneWidth = getResources().getDisplayMetrics().widthPixels;
        //计算出每一格边长
        cellWidth = (phoneWidth - 40) / 9;
        //计算数字位置的偏移量
        tCX = cellWidth / 2;
        tCY = tCX - tCX / 2;

        initPaint();
        invalidate();
    }

    //设定画笔颜色,风格,大小
    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2f);

        mDarkPaint = new Paint();
        mDarkPaint.setColor(Color.parseColor("#52E7CD"));
        mDarkPaint.setStyle(Paint.Style.FILL);

        numberPaint = new Paint();
        numberPaint.setColor(Color.WHITE);
        numberPaint.setTextSize(cellWidth * 0.65f);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        numberPaint.setShadowLayer(10F, -5F, 8F, Color.parseColor("#999999"));
        numberPaint.setAntiAlias(true);

        mOptPaint = new Paint();
        mOptPaint.setColor(Color.WHITE);
        mOptPaint.setTextSize(cellWidth * 0.65f+15);
        mOptPaint.setTextAlign(Paint.Align.CENTER);
        mOptPaint.setShadowLayer(10F, -5F, 8F, Color.parseColor("#999999"));
        mOptPaint.setAntiAlias(true);

        changePaint = new Paint();
        changePaint.setColor(Color.GREEN);
        changePaint.setTextSize(cellWidth * 0.65f);
        changePaint.setTextAlign(Paint.Align.CENTER);
        changePaint.setShadowLayer(10F, -5F, 8F, Color.parseColor("#999999"));
        changePaint.setAntiAlias(true);

        errorNumberPaint = new Paint();
        errorNumberPaint.setColor(Color.RED);
        errorNumberPaint.setTextSize(cellWidth * 0.65f);
        errorNumberPaint.setTextAlign(Paint.Align.CENTER);
        errorNumberPaint.setShadowLayer(10F, -5F, 8F, Color.parseColor("#999999"));
        errorNumberPaint.setAntiAlias(true);

        mOptDarkPaint = new Paint();
        mOptDarkPaint.setColor(Color.parseColor("#52E76E"));
        mOptDarkPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(phoneWidth, phoneWidth + cellWidth +20);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawBoard(canvas);
        //当前点击横坐标
        int x = mOptBoard / 9;
        //当前点击纵坐标
        int y = mOptBoard % 9;
        //判断是否为答题区
        if (gameMap.getOnClicked(x,y)){
            // 画出棋盘选择框
            canvas.drawRect(x * cellWidth +22, y * cellWidth +22, x * cellWidth +20 + cellWidth, y * cellWidth +20 + cellWidth, mOptDarkPaint);
        }

        // 画出当前棋盘数据
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int cutData = gameMap.getCutData(i, j);
                //画正确数字
                if (gameMap.getOnClicked(i,j) && cutData > 0) {
                    canvas.drawText(Integer.toString(cutData), i * cellWidth + tCX+20, j* cellWidth + cellWidth - tCY+20, changePaint);
                }
                //画错误数字
                if (gameMap.getOnClicked(i,j) && cutData > 0 && !gameMap.judgeNumber(i,j,cutData)){
                    canvas.drawText(Integer.toString(cutData), i * cellWidth + tCX+20, j* cellWidth + cellWidth - tCY+20, errorNumberPaint);
                }
            }
        }

        //画出候选区
        drawTrueText(canvas);
    }

    /**
     * 画出候选区
     * @param canvas 画布
     */
    private void drawTrueText(Canvas canvas) {

        // 画平行四边形
        float startY = phoneWidth + 30;
        canvas.drawLine(50, startY, phoneWidth -50, startY, mLinePaint);
        canvas.drawLine(10, startY + cellWidth - 40, phoneWidth -10, startY + cellWidth -40, mLinePaint);
        canvas.drawLine(50, startY, 10, startY + cellWidth - 40, mLinePaint);
        canvas.drawLine(phoneWidth -50, startY, phoneWidth -10, startY + cellWidth -40, mLinePaint);

        //画出候选区文字
        float y = (cellWidth - 30)/2.0f;
        for (int i = 0; i < 9; i++) {
            canvas.drawText(Integer.toString(i + 1), i * cellWidth + tCX+ 20, startY + (cellWidth - tCY) - y, mOptPaint);
        }
    }

    /**
     * 画棋盘
     * @param canvas 画布
     */
    private void drawBoard(Canvas canvas) {
        // 画底色
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int x = i / 3;
                int y = j / 3;
                if ((x == 0 || x == 2) && (y == 0 || y == 2)) {
                    canvas.drawRect(cellWidth * j + 20, 20 + cellWidth * i, cellWidth * j + 20 + cellWidth, 20 + cellWidth * i + cellWidth, mDarkPaint);
                } else if (y == 1 && x == 1) {
                    canvas.drawRect(cellWidth * j + 20, 20 + cellWidth * i, cellWidth * j + 20 + cellWidth, 20 + cellWidth * i + cellWidth, mDarkPaint);
                }
            }
        }

        // 画白线
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(20, cellWidth * i + 1 + 20, 9 * cellWidth + 20,
                    cellWidth * i + 1 + 20, mLinePaint);
            canvas.drawLine(cellWidth * i + 1 + 20, 20, cellWidth * i + 1
                    + 20, 9 * cellWidth + 20, mLinePaint);
        }

        //画初始数字
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!gameMap.getOnClicked(i, j)) {
                    canvas.drawText(gameMap.getText(i, j), i * cellWidth + 20 + tCX, cellWidth * j + 20 + cellWidth - tCY, numberPaint);
                }
            }
        }
    }

    /**
     * 鼠标点击事件
     * @param event 事件
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        if (event.getX() < 20 || event.getY() < 20 || event.getX() > phoneWidth - 20) {
            Log.e("123", "点到边了");
            return super.onTouchEvent(event);
        }

        //当前点击的横坐标
        int choX = (int) ((event.getX() - 20) / cellWidth);
        //当前点击的纵坐标
        int choY = (int) ((event.getY() - 20) / cellWidth);

        Log.i("坐标值：", "("+ choX +","+ choY +")");

        // 棋盘的点击
        if (event.getY() < phoneWidth -20) {
            if (gameMap.getOnClicked(choX, choY)) {
                mOptBoard = choX * 9 + choY;
            }
        }
        // 候选区点击
        else {
            int x = mOptBoard / 9;
            int y = mOptBoard % 9;
            gameMap.setCutData(x, y, choX + 1);
            if (gameMap.judgeNumber(x, y, choX + 1)){
                Log.i("","填入数字正确");
            }
            else {
                Log.i("","填入数字错误");
            }

            if (gameMap.isSuccess()){
                Log.i("","全部完成");
            }
            else {
                Log.i("","未完成");
            }
        }
        //重新绘制地图
        invalidate();
        return true;
    }

    // 再来一局
    public void play() {
        initView();
    }
    // 重头开始
    public void repeat() {
        gameMap.initCutData();
        invalidate();
    }
}
