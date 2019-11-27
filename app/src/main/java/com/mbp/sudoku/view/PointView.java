package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mbp.sudoku.util.PointList;

public class PointView extends View {

    private Paint isPoint;

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
        isPoint =new Paint();
        isPoint.setColor(Color.argb(255,255,255,255));
        isPoint.setTextSize(100);
    }
    protected  void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(200,100);

    }
    PointList pl=new PointList();
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
       int number= pl.addList().size();
       String str=number+"/9";
       canvas.drawText(str,20,80,isPoint);

    }

}
