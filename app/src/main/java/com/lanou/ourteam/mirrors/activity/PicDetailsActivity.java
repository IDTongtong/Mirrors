package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.common.customhem.SmoothImageView;

import com.lanou.ourteam.mirrors.utils.NetHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zt on 16/4/9.
 */
public class PicDetailsActivity extends BaseActivity {
    List<String> mDatas;


    private SmoothImageView imageView;

    @Override
    protected int setContent() {
        return R.layout.activity_pic_detail_lay;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
//        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        mDatas = intent.getStringArrayListExtra("images");
        int mPosition = intent.getIntExtra("position", 0) - 1;
        int mLocationX = intent.getIntExtra("locationX", 0);
        int mLocationY = intent.getIntExtra("locationY", 0);
        int mWidth = intent.getIntExtra("width", 0);
        int mHeight = intent.getIntExtra("height", 0);

        imageView = (SmoothImageView) findViewById(R.id.wear_pic_detail_view);

        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        imageView.transformIn();
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //setContentView(imageView);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(mDatas.get(mPosition), imageView);
//        ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);

    }


    @Override
    protected void initView() {
        overridePendingTransition(0, 0);

    }
}
