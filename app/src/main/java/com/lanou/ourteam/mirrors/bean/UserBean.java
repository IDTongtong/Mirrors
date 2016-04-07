package com.lanou.ourteam.mirrors.bean;

import com.google.gson.Gson;

/**
 * Created by zt on 16/4/6.
 */
public class UserBean {


    /**
     * result : 1
     * msg :
     * data : {"token":"c8fb46a31b660ca6cab3ecdeaed42a41","uid":"68"}
     */

    private String result;
    private String msg;
    /**
     * token : c8fb46a31b660ca6cab3ecdeaed42a41
     * uid : 68
     */

    private DataEntity data;

    public static UserBean objectFromData(String str) {

        return new Gson().fromJson(str, UserBean.class);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        private String token;
        private String uid;

        public static DataEntity objectFromData(String str) {

            return new Gson().fromJson(str, DataEntity.class);
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
