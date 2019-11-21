package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mbp.sudoku.util.GameMap;

import java.util.Arrays;

/**
 * 游戏地图
 * 时间：11-20
 */
public class GameView extends View {

    /** 手机屏幕的宽度 **/
    private int phoneWidth;
    /** 当前格子的宽度 **/
    private int cellWidth;
    /** 手机屏幕的宽度 **/
    private int[] mFalseNumber; // 候选区不能填写的数字
    /** 手机屏幕的宽度 **/
    private Paint mLinePaint; // 白线
    /** 手机屏幕的宽度 **/
    private Paint mDarkPaint; // 浅蓝色的 方格子
    /** 手机屏幕的宽度 **/
    private Paint mOptDarkPaint; // 用户点击 浅绿色的格子
    /** 手机屏幕的宽度 **/
    private Paint numberPaint; // 原始数据 数字
    /** 手机屏幕的宽度 **/
    private Paint changePaint; // 用户填写的数字
    /** 手机屏幕的宽度 **/
    private Paint mOptPaint; // 候选区数字

    private GameMap M; // 用户计算类

    private float tCX;
    private float tCY;
    private int mOptBoard;
    private int mOptNumber;

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

    private void initView() {
        phoneWidth = getResources().getDisplayMetrics().widthPixels;
        Log.d("手机屏幕宽度", String.valueOf(this.phoneWidth));
        cellWidth = (phoneWidth - 40) / 9;

        tCX = cellWidth / 2;
        tCY = tCX - tCX / 2;

        mFalseNumber = new int[9];

        for (int i = 0; i < 9; i++) {
            mFalseNumber[i] = i;
        }

        M = new GameMap();
        initPaint();

        invalidate();
    }

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

        int x = mOptBoard / 9;
        int y = mOptBoard % 9;
        // 画出棋盘选择框
        canvas.drawRect(x * cellWidth +22, y * cellWidth +22, x * cellWidth +20 + cellWidth, y * cellWidth +20 + cellWidth, mOptDarkPaint);

        // 当前棋盘数据
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int cutData = M.getCutData(i, j);
                if (M.getOnClicked(i,j) && cutData>0) {
                    canvas.drawText(Integer.toString(cutData), i * cellWidth + tCX+20, j* cellWidth + cellWidth - tCY+20, changePaint);
                }
            }
        }

        // 候选区文字

        drawTrueText(canvas);

    }

    private void drawTrueText(Canvas canvas) {
        float startY = phoneWidth + 30;

        // 画平行四边形
        canvas.drawLine(50, startY, phoneWidth -50, startY, mLinePaint);
        canvas.drawLine(10, startY + cellWidth - 40, phoneWidth -10, startY + cellWidth -40, mLinePaint);
        canvas.drawLine(50, startY, 10, startY + cellWidth - 40, mLinePaint);
        canvas.drawLine(phoneWidth -50, startY, phoneWidth -10, startY + cellWidth -40, mLinePaint);

        float y = (cellWidth - 30)/2.0f;

        for (int i = 0; i < 9; i++) {
            canvas.drawText(Integer.toString(i + 1), i * cellWidth + tCX+ 20, startY + (cellWidth - tCY) - y, mOptPaint);
        }
    }

    private void drawBoard(Canvas canvas) {

        // 画底色
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int x = i / 3;
                int y = j / 3;
                if ((x == 0 || x == 2) && (y == 0 || y == 2)) {
                    canvas.drawRect(cellWidth * j + 20, 20 + cellWidth * i,
                            cellWidth * j + 20 + cellWidth, 20 + cellWidth
                                    * i + cellWidth, mDarkPaint);
                } else if (y == 1 && x == 1) {
                    canvas.drawRect(cellWidth * j + 20, 20 + cellWidth * i,
                            cellWidth * j + 20 + cellWidth, 20 + cellWidth
                                    * i + cellWidth, mDarkPaint);
                }

            }
        }

        // 画白线
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(20, cellWidth * i + 1 + 20, 9 * cellWidth + 20,
                    cellWidth * i + 1 + 20, mLinePaint);
            canvas.drawLine(cellWidth * i + 1 + 20, 0 + 20, cellWidth * i + 1
                    + 20, 9 * cellWidth + 20, mLinePaint);
        }

        //画初始数字
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!M.getOnClicked(i, j)) {
                    canvas.drawText(M.getText(i, j), i * cellWidth +20+tCX, cellWidth * j + 20+ cellWidth -tCY, numberPaint);

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

        if (event.getX()<20 || event.getY()<20 || event.getX()> phoneWidth -20) {
            Log.e("123", "点到边了");
            return super.onTouchEvent(event);
        }

        int choX = (int) ((event.getX()-20) / cellWidth);
        int choY = (int) ((event.getY()-20) / cellWidth);

        Log.i("game ", "optX "+choX+" optY "+choY);

        if (event.getY() < phoneWidth -20) { // 棋盘的点击

            if (M.getOnClicked(choX, choY)) {
                mFalseNumber = new int[9];
                int[] trueData = M.getFalseData(choY, choX);
                mOptBoard = choX * 9 + choY;
                for (int i : trueData) {
                    mFalseNumber[i - 1] = 1;
                }

            }

        } else { // 候选区点击
            Log.e("game ","opt Number " + choX + 1);

            System.out.println(Arrays.toString(mFalseNumber));

            if (mFalseNumber[choX] == 0) {
                mOptNumber = choX;
                int x = mOptBoard / 9;
                int y = mOptBoard % 9;
                M.setCutData(x, y, mOptNumber+1);
            }

        }
        invalidate();

        return true;
    }


    // 再来一局
    public void play() {
        initView();
    }
    // 重头开始
    public void repeat() {
        M.initCutData();
        invalidate();
    }
}
