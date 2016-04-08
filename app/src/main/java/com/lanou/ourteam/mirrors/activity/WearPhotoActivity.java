package com.lanou.ourteam.mirrors.activity;

import android.support.v7.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseActivity;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by ZHDelete on 16/4/8.
 */
public class WearPhotoActivity extends BaseActivity {
    private JCVideoPlayer videoPlayer;
    private RecyclerView mRecyclerView;

    private NetHelper netHelper;
    private ImageLoader imageLoader;
    private ImageLoader.ImageListener imageListener;



    /**
     * public static void startStoryDetailActivity(Context context,List<StoryDetailText> textList) {

     Intent intent = new Intent(context, StoryDetailActivity.class);
     Bundle bundle = new Bundle();
     bundle.putSerializable("textList", (Serializable) textList);
     intent.putExtras(bundle);
     context.startActivity(intent);

     }
     *
     */


    @Override

    protected int setContent() {
        return R.layout.activity_wear_lay;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        videoPlayer = (JCVideoPlayer) findViewById(R.id.activity_wear_videoplayer);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_wear_recyclerview);

    }
}
