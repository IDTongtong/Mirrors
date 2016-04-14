package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;

import com.lanou.ourteam.mirrors.common.SmoothImageView;
import com.lanou.ourteam.mirrors.utils.NetHelper;



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
        int mPosition = intent.getIntExtra("position", 0) ;
        Log.d("PicDetailsActivity", "适配器传过来的position:  "+ mPosition);
        Log.d("PicDetailsActivity", "图片list 大小:::  "+ mDatas.size());
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
//        netHelper.loadImageWithVolley(imageView,mDatas.get(mPosition - 1));


//        netHelper.loadImageWithPicasso(imageView, mDatas.get(mPosition - 1));

        netHelper.loadBigImageWithPicasso(imageView,mDatas.get(mPosition - 1));

        //universal
        //ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);


    }


    @Override
    protected void initView() {
        overridePendingTransition(0, 0);

    }
}
