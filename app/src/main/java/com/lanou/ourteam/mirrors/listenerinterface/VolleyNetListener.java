package com.lanou.ourteam.mirrors.listenerinterface;

/**
 * Created by ZHDelete on 16/3/31.
 */
public interface VolleyNetListener {
    /**
     * 网络解析 成功或失败的接口
     *
     * @param string
     */
    void onSuccess(String string);

    void onFail(String failStr);
}
