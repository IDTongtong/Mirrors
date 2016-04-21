package com.lanou.ourteam.mirrors.activity;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.common.SmoothImageView;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.List;

/**
 * Created by zt on 16/4/9.
 */
public class PicDetailsActivity extends BaseActivity {
    private List<String> mDatas;
    private SmoothImageView imageView;
    private boolean flag = false;

    @Override
    protected int setContent() {
        return R.layout.activity_pic_detail_lay;
    }

    /**
     * 跳转全屏
     */
    @Override
    protected void initData() {
        Intent intent = getIntent();
        mDatas = intent.getStringArrayListExtra("images");
        int mPosition = intent.getIntExtra("position", 0);
        for (int i = 0; i < mDatas.size(); i++) {
            Log.d("PicDetailsActivity", "图片网址:" + mDatas.get(i));
        }

        int mLocationX = intent.getIntExtra("locationX", 0);
        int mLocationY = intent.getIntExtra("locationY", 0);
        int mWidth = intent.getIntExtra("width", 0);
        int mHeight = intent.getIntExtra("height", 0);
        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        setContentView(imageView);
        NetHelper netHelper = NetHelper.getInstance();
        netHelper.loadBigImageWithPicasso(imageView, mDatas.get(mPosition - 1));


    }

    /**
     * 触摸事件 : 放大图片再点击退出当前activity全屏状态
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (flag == true) {
                    flag = false;
                } else {
                    finish();
                }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置全屏动画
     */
    @Override
    protected void initView() {
        overridePendingTransition(0, 0);

    }
}
