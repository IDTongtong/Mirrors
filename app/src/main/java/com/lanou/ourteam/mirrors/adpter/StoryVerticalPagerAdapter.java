package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.fragment.StoryDetailFragment;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryVerticalPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private List<StoryDetailText> textList;

    public StoryVerticalPagerAdapter(FragmentManager fm, Context context) {

        super(fm);
        this.context = context;
        Log.d("StoryVerticalPagerAdapt", "故事详情vp 是否执行");

    }


    public void initData(List<StoryDetailText> textList) {
        this.textList = textList;


    }

    @Override
    public Fragment getItem(int position) {
        StoryDetailFragment storyDetailFragment = StoryDetailFragment.setDataGetInstance();


        storyDetailFragment.setData(textList, position);

        return storyDetailFragment;

    }


    @Override
    public int getCount() {
        return textList.size();
//        return 10;
    }
}
