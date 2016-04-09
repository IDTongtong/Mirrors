package com.lanou.ourteam.mirrors.activity;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.common.customhem.SmoothImageView;

/**
 * Created by zt on 16/4/9.
 */
public class PicDetailsActivity extends BaseActivity {

    private SmoothImageView imageView;

    @Override
    protected int setContent() {
        return 0;
    }

    @Override
    protected void initData() {
        //  mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        int mPosition = getIntent().getIntExtra("position", 0);
        int mLocationX = getIntent().getIntExtra("locationX", 0);
        int mLocationY = getIntent().getIntExtra("locationY", 0);
        int mWidth = getIntent().getIntExtra("width", 0);
        int mHeight = getIntent().getIntExtra("height", 0);

        imageView = new SmoothImageView(this);
        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setContentView(imageView);
       // ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
    }

    @Override
    protected void initView() {

    }
}
