package com.lanou.ourteam.mirrors.utils;

/**
 * Created by ZHDelete on 16/3/29.
 */
public class Content {
    public static final String URL_HEAD = "http://api101.test.mirroreye.cn/";


    public static final String CATEGORY_LIST = "index.php/products/category_list";
    //浏览平光镜
    public static final String GOODS_LIST = "index.php/products/goods_list";
    //
    public static final String SEND_CODE = "index.php/user/send_code";
    //每日推荐---浏览所有分类: 数据结构,多了一层,
    public static final String MRTJ = "index.php/index/mrtj";
    //菜单:此接口 接口文档没给,从官方软件抓包得到,测试服能否请求到数据不确定,自己试试,出事,不背锅!!!
    public static final String MENU_LIST = "index.php/index/menu_list";

    //专题分析
    public static final String STORY_LIST = "index.php/story/story_list";
    public static final String GOODS_DETIALS_LIST = "index.php/products/goods_info";

   //goods_info 每条商品的 数据

    public static final String GOODS_INFO = "index.php/products/goods_info";
}
