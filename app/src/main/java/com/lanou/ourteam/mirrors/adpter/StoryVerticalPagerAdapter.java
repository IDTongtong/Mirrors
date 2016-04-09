package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.bean.StoryItemBean;
import com.lanou.ourteam.mirrors.fragment.StoryDetailFragment;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryVerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    List<StoryItemBean.DataEntity.StoryDataEntity.TextArrayEntity> text_array;


    public StoryVerticalPagerAdapter(FragmentManager fm, Context context,
                                     List<StoryItemBean.DataEntity.StoryDataEntity.TextArrayEntity> text_array) {

        super(fm);
        this.context = context;
        this.text_array = text_array;
        Log.d("StoryVerticalPagerAdapt", "故事详情vp 是否执行");

    }


//    public void initData(List<StoryDetailText> textList) {
//        this.textList = textList;
//
//
//    }

    @Override
    public Fragment getItem(int position) {
        StoryDetailFragment storyDetailFragment = StoryDetailFragment.setDataGetInstance();


        storyDetailFragment.setData(text_array, position);

        return storyDetailFragment;

    }


    @Override
    public int getCount() {
        return text_array.size();
//        return 10;
    }
}
