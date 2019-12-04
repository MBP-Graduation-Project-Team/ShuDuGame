package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mbp.sudoku.util.PointNumber;


public class PointView extends View {

    /**通关数/关卡数**/
    private Paint levelNumber;
    PointNumber pointNumber = new PointNumber();


    public PointView(Context context) {
        super(context);
        isView();
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isView();
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isView();
    }

    public void isView(){
        levelNumber =new Paint();
        levelNumber.setColor(Color.argb(255,255,255,255));
        levelNumber.setTextSize(100);
    }
    protected  void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(200,100);

    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(pointNumber.getPassNumber() + "/" + pointNumber.getCountNumber(),20,80, levelNumber);
    }
}
