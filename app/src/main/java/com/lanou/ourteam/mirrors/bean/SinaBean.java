package com.lanou.ourteam.mirrors.bean;

/**
 * Created by dllo on 16/4/22.
 */
public class SinaBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"bd0b7fd50f16e16e1096339695360a7b"}
     */

    private String result;
    private String msg;
    /**
     * token : bd0b7fd50f16e16e1096339695360a7b
     */

    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
