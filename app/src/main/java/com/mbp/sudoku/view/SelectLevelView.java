package com.mbp.sudoku.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.mbp.sudoku.entity.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择关卡视图
 */
public class SelectLevelView extends View {

    Button button;
    /** **/
    private Paint titleNumberPaint;
    /** **/
    private Paint boxPaint;
    /** **/
    private Paint levelNumberPaint;
    /** **/
    private Paint goodTimePaint;
    /** **/
    private Paint lockPaint;
    /** **/
    /** **/
    /** **/
    /** **/
    /** **/
    /** **/
    /** **/
    /** **/
    /** **/
    int rowNumber = 5;
    int phoneWidth = getResources().getDisplayMetrics().widthPixels;
    int viewSpace = 80;
    int boxSpace = viewSpace / 4;
    int boxWidth = ((phoneWidth - viewSpace) - ((rowNumber - 1) * boxSpace))/rowNumber;

    int numberX = boxWidth / 2;
    int numberY = numberX - numberX / 2;

    /**
     * 构造方法,XML引用时调用
     * @param context 上下文
     * @param attrs 属性
     */
    public SelectLevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    private void initPaint() {
        titleNumberPaint = new Paint();
        titleNumberPaint.setColor(Color.BLACK);

        boxPaint = new Paint();
        boxPaint.setColor(Color.BLUE);

        levelNumberPaint = new Paint();
        levelNumberPaint.setColor(Color.WHITE);
        levelNumberPaint.setTextSize(boxWidth * 0.5f);

        goodTimePaint = new Paint();
        goodTimePaint.setColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //40,0,100,100,
        //120,0,220,100
        //240,0,340,100
        for (int i = 0; i < 5; i++) {
            canvas.drawRect(i * (boxWidth + boxSpace) + viewSpace / 2,0,i * (boxWidth + boxSpace) + boxWidth + viewSpace / 2,boxWidth,boxPaint);
            canvas.drawText(String.valueOf(i + 1),i * boxWidth + numberX + viewSpace / 2,boxWidth / 3,levelNumberPaint);
        }
    }
}
//73   34  13  18