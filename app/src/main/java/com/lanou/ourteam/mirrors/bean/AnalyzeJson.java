package com.lanou.ourteam.mirrors.bean;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZHDelete on 16/3/31.
 */
public class AnalyzeJson {

    private Gson gson;


    GoodsListAllBean goodsListAllBean;
    MrtjBean mrtjBean;
    StoryBean storyBean;




    public AnalyzeJson() {
        super();
        gson = new Gson();
    }

    public GoodsListAllBean analyzeGoodsList(String string) {
        Log.d("AnanlyzeJson", "***" + string);

        try {
            JSONObject jsonObject = new JSONObject(string);
            goodsListAllBean = gson.fromJson(jsonObject.toString(), GoodsListAllBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goodsListAllBean;
    }



    public MrtjBean AnalyzeMrtj(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            mrtjBean = gson.fromJson(jsonObject.toString(), MrtjBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mrtjBean;

    }


    public StoryBean AnalyzeStory(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            storyBean = gson.fromJson(jsonObject.toString(), StoryBean.class);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return storyBean;
    }

}
