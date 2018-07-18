package com.tag.tai.tag.Common;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.tag.tai.tag.Fragments.Home.SwipeCallback;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private ItemCode itemmcode;
    private SwipeCallback callback;

    public GestureListener(ItemCode itemmcode, SwipeCallback callback) {
        this.itemmcode = itemmcode;
        this.callback = callback;
    }

    public enum ItemCode{
        HANGOUTS,
        SERVICES,
        SHOPPINNG
    }

    public enum Direction{
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        TAPPED
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Log.d("12345", "onScroll: ");
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("12345", "onLongPress: ");
        super.onLongPress(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        Log.d("12345", "onFling: right");
                        callback.swipedItem(Direction.RIGHT,itemmcode);
                    } else {
                        Log.d("12345", "onFling: left");
                        callback.swipedItem(Direction.LEFT,itemmcode);
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    Log.d("12345", "onFling: bottom");
                    callback.swipedItem(Direction.BOTTOM,itemmcode);
                } else {
                    Log.d("12345", "onFling: top");
                    callback.swipedItem(Direction.TOP,itemmcode);
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }



    @Override
    public boolean onDown(MotionEvent e) {
        //Log.d("12345", "onDown: ");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("12345", "onSingleTapConfirmed: ");

        callback.swipedItem(Direction.TAPPED,itemmcode);

        return super.onSingleTapConfirmed(e);
    }
}
