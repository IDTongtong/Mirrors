package com.lanou.ourteam.mirrors.selfview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZHDelete on 16/3/30.
 */
public class VerticalPager extends ViewPager {

    float startX;
    float startY;

    public VerticalPager(Context context) {
        super(context);
        init();
    }

    public VerticalPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // The majority of the magic happens here
        setPageTransformer(true, new VerticalPageTransformer());
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                //该页 正在从左侧 出屏
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                //该页 正在从右侧 出屏
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        //Return the width of the your view.组件宽度
        float height = getHeight();
        //getY() 距屏幕顶端距离

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    /**
     * android系统中的每个View的子类都具有下面三个和TouchEvent处理密切相关的方法：
     * 1）public boolean dispatchTouchEvent(MotionEvent ev)  这个方法用来分发TouchEvent
     * 2）public boolean onInterceptTouchEvent(MotionEvent ev) 这个方法用来拦截TouchEvent
     * 事件拦截：public boolean onInterceptTouchEvent(MotionEvent ev)
     * 当前view拦截到事件后，处理流程如下：
     * return false；表示放行由当前view的子view的dispatchTouchEvent分发处理。
     * return true或return super.onInterceptTouchEvent()表示拦截该事件，由该View的Ontouch方法处理。
     * 3）public boolean onTouchEvent(MotionEvent ev) 这个方法用来处理TouchEvent
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                if (Math.abs(moveY - startY) - Math.abs(moveX - startX) > 0) {
                    return true;
                }
                break;
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

}

