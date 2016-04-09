package com.lanou.ourteam.mirrors.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseFragment;
import com.lanou.ourteam.mirrors.bean.StoryBean;
import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.bean.StoryItemBean;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import java.util.List;

/**
 * Created by ZHDelete on 16/4/6.
 */
public class StoryDetailFragment extends BaseFragment{


    List<StoryItemBean.DataEntity.StoryDataEntity.TextArrayEntity> text_array;
    private TextView smallTitleTv, titleTv, subTitleTv;
    String smallTitle, title, subTitle;


    public static StoryDetailFragment setDataGetInstance() {

        StoryDetailFragment instance = new StoryDetailFragment();
        return instance;
    }

    public void setData( List<StoryItemBean.DataEntity.StoryDataEntity.TextArrayEntity> text_array, int position) {

        this.text_array = text_array;
        smallTitle = text_array.get(position).getSmallTitle();
        title = text_array.get(position).getTitle();
        subTitle = text_array.get(position).getSubTitle();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        smallTitleTv = bindViewById(view, R.id.fragment_story_detail_smallTitle);
        titleTv = bindViewById(view, R.id.fragment_story_detail_title);
        subTitleTv = bindViewById(view, R.id.fragment_story_detail_subTitle);

        smallTitleTv.setText(smallTitle);
        titleTv.setText(title);
        subTitleTv.setText(subTitle);


    }





    @Override
    protected int setContent() {
        return R.layout.fragment_story_detail_lay;
    }
}
