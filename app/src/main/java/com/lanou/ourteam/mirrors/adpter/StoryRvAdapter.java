package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.activity.StoryDetailActivity;
import com.lanou.ourteam.mirrors.bean.StoryBean;
import com.lanou.ourteam.mirrors.bean.StoryDetailText;
import com.lanou.ourteam.mirrors.imagedao.DaoEntityHelper;
import com.lanou.ourteam.mirrors.imagedao.StoryItemEntity;
import com.lanou.ourteam.mirrors.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZHDelete on 16/4/1.
 */
public class StoryRvAdapter extends BaseRecyclerAdapter<StoryBean.DataEntity.ListEntity> {

    private StoryBean.DataEntity.ListEntity listEntity;
    //serilizable
    List<StoryDetailText> textList = new ArrayList<>();

    private DaoEntityHelper daoEntityHelper;

    List<StoryItemEntity> storyItemEntityList;


    public StoryRvAdapter(Context context) {
        super(context);
        daoEntityHelper = DaoEntityHelper.getInstance();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.story_item_view, parent, false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        listEntity = list.get(position);

//        //第一条数据有问题
//        if (position != 0) {
//            Log.d("StoryRvAdapter","文字" + list.get(position).getStory_data().getText_array().size()+
//                    "图片:" + list.get(position).getStory_data().getImg_array().size());
//            //serilizable相关
//            List<StoryBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity> textArrayEntityList =
//                    list.get(position).getStory_data().getText_array();
//            List<String> img_array = list.get(position).getStory_data().getImg_array();
//
//            for (int i = 0; i < textArrayEntityList.size(); i++) {
//                StoryDetailText storyDetailText = new StoryDetailText();
//                StoryBean.DataEntity.ListEntity.StoryDataEntity.TextArrayEntity temp =
//                        textArrayEntityList.get(i);
//                storyDetailText.setCategory(temp.getCategory());
//                storyDetailText.setColorTitle(temp.getColorTitle());
//                storyDetailText.setColorTitleColor(temp.getColorTitleColor());
//                storyDetailText.setSmallTitle(temp.getSmallTitle());
//                storyDetailText.setSubTitle(temp.getSubTitle());
//                storyDetailText.setTitle(temp.getTitle());
//                storyDetailText.setTitleColor(temp.getTitleColor());
//                storyDetailText.setVerticalTitle(temp.getVerticalTitle());
//                storyDetailText.setVerticalTitleColor(temp.getVerticalTitleColor());
//                storyDetailText.setImg_array(img_array.get(i));
//                textList.add(storyDetailText);
//            }
//
//
//
//        }
//        Log.d("StoryRvAdapter", "list 的大小" + list.size());
//        Log.d("StoryRvAdapter", "***"+list.get(1).getStory_img());




//        String goods_img = listEntity.getStory_img();
//        Log.d("MrtjRvAdapter", "图片网址:" + goods_img);
//        imageLoader.get(goods_img, imageListener);
//
//        storyViewHolder.story_titleTv.setText(listEntity.getStory_title());
//
//
//        //跳转到下一层的相关
//        storyViewHolder.picIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StoryDetailActivity.startStoryDetailActivity(context, textList);
//                //TODO 传值 到activity  不会了 ,现在,cfy 说: 序列化 所有类,和其内部类
//                //暂时用静态常量传吧
//                //改为新建一个类 序列化
//
//            }
//        });
        StoryViewHolder storyViewHolder = (StoryViewHolder) holder;


//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(
//                storyViewHolder.picIv,
//                R.mipmap.ic_launcher,
//                R.mipmap.loading
//
//        );
        if (CommonUtils.isNetworkAvailable()) {

            listEntity = list.get(position);
            final String story_id = listEntity.getStory_id();
            Log.d("StoryRvAdapter", "QQQQ" + story_id);

            //bindview 相关 from here
            //第一条数据有问题
            if (position != 0) {
                Log.d("StoryRvAdapter", "文字" + list.get(position).getStory_data().getText_array().size() +
                        "图片:" + list.get(position).getStory_data().getImg_array().size());
            }


            Log.d("StoryRvAdapter", "list 的大小" + list.size());
            Log.d("StoryRvAdapter", "***" + list.get(1).getStory_img());


            String goods_img = listEntity.getStory_img();
            Log.d("StoryRvAdapter", "图片网址:" + goods_img);

//            imageLoader.get(goods_img, imageListener,400,200);
            netHelper.loadImageWithVolley(storyViewHolder.picIv,goods_img);

            storyViewHolder.story_titleTv.setText(listEntity.getStory_title());

            //跳转到下一层的相关
            storyViewHolder.picIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //StoryDetailActivity.startStoryDetailActivity(context, textList);
                    //TODO 传值 到activity  不会了 ,现在,cfy 说: 序列化 所有类,和其内部类
                    //新建一个类 序列化


                    //在 acitivity里  再解析 故事详情
                    StoryDetailActivity.startStoryDetailActivity(context, story_id);

                }
            });
            //bindview 相关 up here


            //有网时存入数据库相关
            //daoEntityHelper.deleteStoryAll();
            //给StoryItem数据库添加数据:
            StoryItemEntity storyItemEntity = new StoryItemEntity();
            String story_title = listEntity.getStory_title();
            storyItemEntity.setStory_img(goods_img);
            storyItemEntity.setStory_title(story_title);

            if (daoEntityHelper.queryStoryItemByUrl(storyItemEntity)) {
                Log.d("StoryRvAdapter", "该 story_url 已在数据库中存在");
            } else {
                daoEntityHelper.insert(storyItemEntity);
                Log.d("StoryRvAdapter", "---" + storyItemEntity.getStory_img());
                Log.d("StoryRvAdapter", "story fragment 封面插入数据库执行");

            }
        } else {
            //没网时 从数据库取
            StoryItemEntity storyItemEntity = storyItemEntityList.get(position);
            storyViewHolder.story_titleTv.setText(storyItemEntity.getStory_title());

//            imageLoader.get(storyItemEntity.getStory_img(), imageListener);
            netHelper.loadImageWithVolley(storyViewHolder.picIv,storyItemEntity.getStory_img());


            storyViewHolder.picIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "没有网点不进去的", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        if (CommonUtils.isNetworkAvailable()) {
            return isLength() ? list.size() : 0;

        } else {
            storyItemEntityList = daoEntityHelper.queryStory();
            return storyItemEntityList.size();
        }
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
