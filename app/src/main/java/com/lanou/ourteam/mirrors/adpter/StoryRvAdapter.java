package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.bean.StoryBean;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class StoryRvAdapter extends  BaseRecyclerAdapter<StoryBean.DataEntity.ListEntity> {

    public StoryRvAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.story_item_view, parent, false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoryBean.DataEntity.ListEntity listEntity = list.get(position);
        StoryViewHolder storyViewHolder = (StoryViewHolder) holder;


        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
                storyViewHolder.picIv,
                R.mipmap.ic_launcher,
                R.mipmap.loading

        );
        String goods_img = listEntity.getStory_img();
        Log.d("MrtjRvAdapter", "图片网址:" + goods_img);
        imageLoader.get(goods_img, imageListener);

        storyViewHolder.story_titleTv.setText(listEntity.getStory_title());

    }

    @Override
    public int getItemCount() {
        return isLength() ? list.size() : 0;
    }
    class StoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;
        private TextView story_titleTv;

        public StoryViewHolder(View itemView) {
            super(itemView);
            picIv = (ImageView) itemView.findViewById(R.id.story_item_pic);
            story_titleTv = (TextView) itemView.findViewById(R.id.story_item_story_title_tv);
        }
    }


}
