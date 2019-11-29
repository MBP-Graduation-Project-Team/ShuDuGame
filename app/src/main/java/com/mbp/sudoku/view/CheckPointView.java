package com.mbp.sudoku.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.mbp.sudoku.R;
import com.mbp.sudoku.activity.GameActivity;
import com.mbp.sudoku.util.PointNumber;

/**
 * 选择关卡界面
 */
public class CheckPointView extends View {

    /**图标-锁**/
    private Paint lockBg;
    private Paint unlockNum;
    private Paint unlockBg;
    private Paint passedNum;
    private Paint passedTime;
    private Paint passedBg;
    private Bitmap lockPic;
    private int lockWidth,lockHeight;
    PointNumber pointNumber = new PointNumber();



    public CheckPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initView();
    }

    public CheckPointView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        initView();
    }

    public CheckPointView(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        passedBg=new Paint();
        passedBg.setColor(Color.argb(225,137,207,240));
        passedNum=new Paint();
        passedNum.setColor(Color.argb(255,255,255,255));
        passedNum.setTextSize(100);
        passedTime=new Paint();
        passedTime.setColor(Color.argb(255,255,255,255));
        passedTime.setTextSize(50);

        unlockNum=new Paint();
        unlockNum.setColor(Color.argb(255,255,255,255));
        unlockNum.setTextSize(150);
        unlockBg=new Paint();
        unlockBg.setColor(Color.argb(105,0,0,0));

        lockBg=new Paint();
        lockBg.setColor(Color.argb(155,0,0,0));

        lockPic=BitmapFactory.decodeResource(getResources(), R.drawable.lock);
        lockWidth=lockPic.getWidth();
        lockHeight=lockPic.getHeight();
    }



    protected  void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(800,800);

    }

    int next_passed_i;
    int next_passed_j;
    int next_lock_i;
    int next_lock_j;
    int next_unlock_i;
    int next_unlock_j;
    PointNumber pn = new PointNumber();


    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        int width = 200;
        int height = 200;
        int poswidth=35;
        int posheight=30;
        int next= Integer.valueOf(PointNumber.getPassNumber());

        for(int k=next;k<=9;k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (3 * i + j + 1 == next && k==next && next<=9) {
                        next_passed_i = i;
                        next_passed_j = j;
//                         System.out.println("i:" + next_passed_i + "J=" + next_passed_j);
                    }
                    if (3 * i + j + 1 == next+1 && k==next+1  && next+1<=9) {
                        next_lock_i = i;
                        next_lock_j = j;
//                         System.out.println("i:" + next_lock_i + "J=" + next_lock_j);
                    }
                    if (3 * i + j + 1 == next+2 && k==next+2 && next+2<=9) {

                        next_unlock_i = i;
                        next_unlock_j = j;
//                         System.out.println("i:" + next_unlock_i + "J=" + next_unlock_j);
                    }
                }
            }
        }

        for(int i=0;i<next_passed_i;i++){
            for(int j=0;j<3;j++){
                    canvas.drawRect(j * (width + 100), i * (height + 100), j * (width + 100) + width, i * (height + 100) + height, passedBg);
                    canvas.drawText(pn.getText(i, j), j * (width + 100) + 70, i * (height + 100) + 100, passedNum);
                    canvas.drawText(PointNumber.getMapList().get(pn.getPoint(i,j)-1).getGoodTime(), j * (width + 100) + 40, i * (height + 100) + 170, passedTime);
                }
            }
            for(int i=next_passed_i;i<next_passed_i+1;i++){
                for(int j=0;j<next_passed_j+1;j++){

                    canvas.drawRect(j * (width + 100), i * (height + 100), j * (width + 100) + width, i * (height + 100) + height, passedBg);
                    canvas.drawText(pn.getText(i, j), j * (width + 100) + 70, i * (height + 100) + 100, passedNum);

                    canvas.drawText(PointNumber.getMapList().get(pn.getPoint(i,j)-1).getGoodTime(), j * (width + 100) + 40, i * (height + 100) + 170, passedTime);
                }
            }

        if(next+1<9) {
            for (int i = next_lock_i; i < next_lock_i + 1; i++) {
                for (int j = next_lock_j; j < next_lock_j + 1; j++) {
                    canvas.drawRect(j * (width + 100), i * (height + 100), j * (width + 100) + width, i * (height + 100) + height, unlockBg);
                    canvas.drawText(pn.getText(i, j), j * (width + 100) + 60, i * (height + 100) + 150, unlockNum);

                }
            }
        }
        if(next+2<10) {
            for (int i = next_unlock_i; i < next_unlock_i + 1; i++) {
                for (int j = next_unlock_j; j < 3; j++) {

                    canvas.drawRect(j * (width + 100), i * (height + 100), j * (width + 100) + width, i * (height + 100) + height, lockBg);
                    canvas.drawBitmap(lockPic, new Rect(0, 0, lockWidth * 10, lockHeight * 10), new Rect(j * (width + 100) + poswidth, i * (height + 100) + posheight, j * (width + 100) + poswidth + lockWidth, i * (height + 100) + posheight + lockHeight), null);

                }
            }

            for (int i = next_unlock_i + 1; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    canvas.drawRect(j * (width + 100), i * (height + 100), j * (width + 100) + width, i * (height + 100) + height, lockBg);
                    canvas.drawBitmap(lockPic, new Rect(0, 0, lockWidth * 10, lockHeight * 10), new Rect(j * (width + 100) + poswidth, i * (height + 100) + posheight, j * (width + 100) + poswidth + lockWidth, i * (height + 100) + posheight + lockHeight), null);

                }
            }
        }
    }

    public boolean onTouchEvent (MotionEvent event){
        int num;
        int width = 200;
        int height = 200;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                //canvas.drawRect(j*(width+100),i*(height+100),j*(width+100)+width,i*(height+100)+height,unlockBg);
                float x=event.getX();
                float y = event.getY();
                for(int i=0;i<3;i++) {
                    for (int j = 0; j < 3; j++) {
                        if (x >= j*(width+100) && x <= j*(width+100)+width && y >= i*(height+100) && y <= i*(height+100)+height) {
                            num =3*i+j+1;
                            Log.d("CheckPointView num:", String.valueOf(num));
                            Intent intent = new Intent(getContext(), GameActivity.class);
                            intent.putExtra("level",num);
                            getContext().startActivity(intent);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}


