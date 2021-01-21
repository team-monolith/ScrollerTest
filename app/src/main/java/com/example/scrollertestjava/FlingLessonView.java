package com.example.scrollertestjava;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class FlingLessonView extends View {

    private static final int ANIMATION_INTERVAL = 10;
    private final Handler handler = new Handler();
    private Scroller scroller;
    private GestureDetector gestureDetector;

    private float currX;
    private float currY;

    /**
     * @param context
     */
    public FlingLessonView(Context context) {
        super(context);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public FlingLessonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * ビューの初期化
     * @param context
     */
    private void init(Context context){

        setLongClickable(true);
        scroller = new Scroller(context);
        gestureDetector = new GestureDetector(context,new OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                    float distanceY) {

                if(!scroller.isFinished())
                    scroller.abortAnimation();

                currX = e2.getX();
                currY = e2.getY();

                invalidate();

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {

                scroller.fling(
                        (int)e2.getX(),
                        (int)e2.getY(),
                        (int)velocityX,
                        (int)velocityY,
                        0, getWidth(), 0, getHeight());

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        scroller.computeScrollOffset();
                        currX = scroller.getCurrX();
                        currY = scroller.getCurrY();

                        invalidate();

                        if(!scroller.isFinished()){
                            handler.postDelayed(this, ANIMATION_INTERVAL);
                        }
                    }
                });

                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }

    private final Paint paint = new Paint();
    {
        paint.setStyle(Style.FILL);
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(currX, currY, 60.f, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return
                gestureDetector.onTouchEvent(event) ||
                        super.onTouchEvent(event);
    }
}