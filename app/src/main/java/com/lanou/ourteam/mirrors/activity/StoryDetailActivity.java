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
import com.lanou.ourteam.mirrors.bean.AnalyzeJson;
import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.bean.StoryItemBean;
import com.lanou.ourteam.mirrors.common.customhem.VerticalViewPager;
import com.lanou.ourteam.mirrors.listenerinterface.VolleyNetListener;
import com.lanou.ourteam.mirrors.utils.Content;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryDetailActivity extends BaseActivity {
    private VerticalViewPager mVerticalPager;
    private StoryVerticalPagerAdapter mAdapter;
    private Context context;

    private ImageView backIv;
    private NetHelper netHelper;
//    private ImageLoader imageLoader;
    private String backIv_url;

    List<String> img_array;
    List<StoryItemBean.DataEntity.StoryDataEntity.TextArrayEntity> text_array;

    ImageLoader.ImageListener imageListener;
    private String story_id;
    StoryItemBean storyItemBean;





    public static void startStoryDetailActivity(Context context,List<StoryDetailText> textList) {

        Intent intent = new Intent(context, StoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("textList", (Serializable) textList);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    public static void startStoryDetailActivity(Context context, String story_id) {

        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra("story_id", story_id);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {

        context = this;
        mVerticalPager = (VerticalViewPager) findViewById(R.id.activity_story_detail_vertical_viewpger);
        netHelper = NetHelper.getInstance();

        backIv = (ImageView) findViewById(R.id.activity_story_detail_back_iv);

        imageListener = ImageLoader.getImageListener(
                backIv,
                R.mipmap.loading,
                R.mipmap.ic_launcher

        );

//        imageLoader = netHelper.getImageLoader();

//        Bundle bundle = getIntent().getExtras();
//        textList = (List<StoryDetailText>) bundle.getSerializable("textList");
//        Log.d("StoryDetailActivity", "是否传过来serilizable+大小:" + textList.size());

        story_id = getIntent().getStringExtra("story_id");
        Log.d("StoryDetailActivity", "456789   " + story_id);


        Map<String, String> params = new HashMap();

        params.put("token", "");
        params.put("device_type", "3");
        params.put("story_id", story_id);
        params.put("version", "1.0.1");

        netHelper.volleyPostTogetNetData(Content.STORY_INFO, params, new VolleyNetListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("StoryDetailActivity", "String 故事详情:" + string);

                AnalyzeJson ananlyzeJson = AnalyzeJson.getInstance();
                storyItemBean = ananlyzeJson.analyzeStoryItem(string);


                img_array = storyItemBean.getData().getStory_data().getImg_array();
                text_array = storyItemBean.getData().getStory_data().getText_array();
                Log.d("StoryDetailActivity", "TTTT  " + text_array.size());

                mAdapter = new StoryVerticalPagerAdapter(getSupportFragmentManager(), context, text_array);
                backIv_url = img_array.get(0);//默认第一次加载一张图
                Log.d("StoryDetailActivity", "第一张图:" + backIv_url);
                mVerticalPager.setAdapter(mAdapter);

                //有网时 通过网络拉取图片,没网时 进不来 保证 进来 就有图,而不是 viewpager滑一次 才有图
//                imageLoader.get(backIv_url, imageListener, 768, 1280);
                netHelper.loadImageWithVolley(backIv,backIv_url);

            }

            @Override
            public void onFail(String failStr) {

            }
        });



        Log.d("StoryDetailActivity", "---" + backIv_url);


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

                backIv_url = img_array.get(position);
//                imageLoader.get(backIv_url, imageListener);
                netHelper.loadImageWithVolley(backIv,backIv_url);


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
