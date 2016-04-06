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
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.activity.StoryDetailActivity;
import com.lanou.ourteam.mirrors.bean.StoryBean;
import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class StoryRvAdapter extends BaseRecyclerAdapter<StoryBean.DataEntity.ListEntity> {

    private StoryBean.DataEntity.ListEntity listEntity;
    //serilizable
    List<StoryDetailText> textList = new ArrayList<>();

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
        listEntity = list.get(position);

        //第一条数据有问题
        if (position != 0) {
            Log.d("StoryRvAdapter","文字" + list.get(position).getStory_data().getText_array().size()+
                    "图片:" + list.get(position).getStory_data().getImg_array().size());
            //serilizable相关
            List<StoryBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity> textArrayEntityList =
                    list.get(position).getStory_data().getText_array();
            List<String> img_array = list.get(position).getStory_data().getImg_array();

            for (int i = 0; i < textArrayEntityList.size(); i++) {
                StoryDetailText storyDetailText = new StoryDetailText();
                StoryBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity temp =
                        textArrayEntityList.get(i);
                storyDetailText.setCategory(temp.getCategory());
                storyDetailText.setColorTitle(temp.getColorTitle());
                storyDetailText.setColorTitleColor(temp.getColorTitleColor());
                storyDetailText.setSmallTitle(temp.getSmallTitle());
                storyDetailText.setSubTitle(temp.getSubTitle());
                storyDetailText.setTitle(temp.getTitle());
                storyDetailText.setTitleColor(temp.getTitleColor());
                storyDetailText.setVerticalTitle(temp.getVerticalTitle());
                storyDetailText.setVerticalTitleColor(temp.getVerticalTitleColor());
                storyDetailText.setImg_array(img_array.get(i));
                textList.add(storyDetailText);
            }



        }
        Log.d("StoryRvAdapter", "list 的大小" + list.size());
        Log.d("StoryRvAdapter", "***"+list.get(1).getStory_img());


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


        //跳转到下一层的相关
        storyViewHolder.picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoryDetailActivity.startStoryDetailActivity(context, textList);
                //TODO 传值 到activity  不会了 ,现在,cfy 说: 序列化 所有类,和其内部类
                //暂时用静态常量传吧
                //改为新建一个类 序列化

            }
        });

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
