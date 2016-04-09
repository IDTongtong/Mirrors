package com.lanou.ourteam.mirrors.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.lanou.ourteam.mirrors.R;
import com.lanou.ourteam.mirrors.base.BaseRecyclerAdapter;
import com.lanou.ourteam.mirrors.bean.GoodsItemBean;
import com.lanou.ourteam.mirrors.utils.NetHelper;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by ZHDelete on 16/4/8.
 */
public class WearPhoRvAdapter extends BaseRecyclerAdapter<GoodsItemBean.DataEntity.WearVideoEntity> {
    private static final int TYPE_VIDEO = 0x111;
    private static final int TYPE_PICTURE = 0x222;




    private Context context;
    private LayoutInflater inflater;
    private NetHelper netHelper;
    private ImageLoader imageLoader;

    public WearPhoRvAdapter(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
        netHelper = NetHelper.getInstance();
        imageLoader = netHelper.getImageLoader();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_VIDEO;
            default:
                return TYPE_PICTURE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {

            case TYPE_VIDEO:
                View itemVideo = inflater.inflate(R.layout.wearphoto_item_video, parent, false);
                viewHolder =  new VideoHolder(itemVideo);
            break;
            case TYPE_PICTURE:
                View itemPic = inflater.inflate(R.layout.wearphoto_item_pic, parent, false);
                viewHolder =  new CommonHolder(itemPic);
            break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_VIDEO:
                VideoHolder videoHolder = (VideoHolder) holder;
                videoHolder.jcVideoPlayer.setUp(list.get(position).getData(), "", false);
                Log.d("WearPhoRvAdapter", "视频地址:" + list.get(position).getData());
                break;
            case TYPE_PICTURE:
                CommonHolder commonHolder = (CommonHolder) holder;
//                ImageLoader.ImageListener imageListener = imageLoader.getImageListener(
//                        commonHolder.imageView,
//                        R.mipmap.loading,
//                        R.mipmap.ic_launcher
//                );
//                imageLoader.get(list.get(position).getData(), imageListener, 1280, 1280);

                netHelper.loadImageWithVolley(commonHolder.imageView, list.get(position).getData());
                break;

        }
    }

    @Override
    public int getItemCount() {
        return isLength() ? list.size() : 0;
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private JCVideoPlayer jcVideoPlayer;

        public VideoHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = (JCVideoPlayer) itemView.findViewById(R.id.wear_item_video_video_player);
        }
    }

    class CommonHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public CommonHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.wear_item_pic_iv);
        }
    }
}






