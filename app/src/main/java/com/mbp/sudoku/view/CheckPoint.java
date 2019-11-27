package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CheckPoint extends View {

    public CheckPoint(Context context) {
        super(context);
    }

    public CheckPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满
        /*canvas.drawRect(0, 0, 200, 200, p);// 正方形
        canvas.drawRect(220, 0, 420, 200, p);// 正方形
        canvas.drawRect(440, 0, 640, 200, p);// 正方形
        canvas.drawRect(660, 0, 860, 200, p);// 正方形*/
        for (int i = 0; i < 4; i++) {
            canvas.drawRect(i * (200 + 20), 0, (i + 1) * 200 + i * 20, 200, p);
        }
    }
}
