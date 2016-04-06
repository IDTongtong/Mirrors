package com.lanou.ourteam.mirrors.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.adpter.StoryVerticalPagerAdapter;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.common.customhem.VerticalViewPager;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryDetailActivity extends BaseActivity {
    private VerticalViewPager mVerticalPager;
    private StoryVerticalPagerAdapter mAdapter;
    private Context context;

    private ImageView backIv;
    private NetHelper netHelper;
    private ImageLoader imageLoader;
    private String backIv_url;

    List<StoryDetailText> textList;

    ImageLoader.ImageListener imageListener;

    public static void startStoryDetailActivity(Context context,List<StoryDetailText> textList) {

        Intent intent = new Intent(context, StoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("textList", (Serializable) textList);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {
        mVerticalPager = (VerticalViewPager) findViewById(R.id.activity_story_detail_vertical_viewpger);
        backIv = (ImageView) findViewById(R.id.activity_story_detail_back_iv);

        Bundle bundle = getIntent().getExtras();
        textList = (List<StoryDetailText>) bundle.getSerializable("textList");
        Log.d("StoryDetailActivity", "是否传过来serilizable+大小:" + textList.size());



        mAdapter = new StoryVerticalPagerAdapter(getSupportFragmentManager(),context);

        mAdapter.initData(textList);

        mVerticalPager.setAdapter(mAdapter);

        backIv_url = textList.get(0).getImg_array();
        Log.d("StoryDetailActivity", "---" + backIv_url);
        netHelper = NetHelper.getInstance();
        imageListener = ImageLoader.getImageListener(
                backIv,
                R.mipmap.loading,
                R.mipmap.ic_launcher

        );
        imageLoader = netHelper.getImageLoader();
        imageLoader.get(backIv_url, imageListener);

    }

    @Override
    protected void initData() {
        mVerticalPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d("StoryDetailActivity", "1");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("StoryDetailActivity", "2");

                backIv_url = textList.get(position).getImg_array();
                imageLoader.get(backIv_url, imageListener);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d("StoryDetailActivity", "3");

            }
        });

    }

    @Override
    protected int setContent() {
        return R.layout.activity_story_detail_lay;
    }
}
