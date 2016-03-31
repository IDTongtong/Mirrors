package com.lanou.ourteam.mirrors.common.customhem;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dllo on 16/3/30.
 */
public class VerticalViewPager extends ViewPager {
    private float startX,startY;


    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //设置页面滑动时候的动画
        //true为有序
        setPageTransformer(true, new VerticalPageTransformer());
        //设置此观点的过度滚动模式。有效过滚动模式反弹时始终（默认），
        // OVER_SCROLL_IF_CONTENT滚动（仅允许过滚动如果视图含量比容器大），
        // 或OVER_SCROLL_NEVER。设置一个视图的过滚动模式将只有如果视图是能够滚动的效果。

        setOverScrollMode(OVER_SCROLL_NEVER);
    }
//PageTransformer接口，用来自定义ViewPager页面切换动画，
// 用setPageTransformer(boolean, PageTransformer)方法来进行设置；
    private class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);

                page.setTranslationX(page.getWidth() * -position);

                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
//x y 变换位置
        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(event));
        swapXY(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                if (Math.abs(moveY - startY) - Math.abs(moveX - startX) > 0) {
                    //return flase不拦截，true就是拦截
                    return true;
                }
                break;
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startX = (event.getY() / getHeight()) * getWidth();
                startY = (event.getY() / getWidth()) * getHeight();
                break;
        }
        return super.onTouchEvent(swapXY(event));
    }


}
