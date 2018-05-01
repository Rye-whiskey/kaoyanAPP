package com.example.sjl94.kaoyan.api;

/**
 * Created by sjl94 on 2018/3/27.
 */

public class Api {
    /*配置baseUl*/
    public static final String BASE_URL="http://193.112.122.190:3000/";
    /*用户登录*/
    public static final String LOGIN=BASE_URL + "login";
    /*用户注册*/
    public static final String REGISTER=BASE_URL + "register";
    /*添加请假记录*/
    public static final String PUBLISH=BASE_URL + "publish";

    public static final String PUBLISH_WEIZHI=BASE_URL + "publish_weizhi";

    public static final String PUBLISH_COMMENT=BASE_URL + "publish_comment";
    /*获得用户请求记录*/
    public static final String SEARCH=BASE_URL + "search";

    public static final String SEARCH_WEIZHI=BASE_URL + "search_weizhi";

    public static final String VAC=BASE_URL + "vac";

    public static final String SEARCH_MATH=BASE_URL + "search_math";


    public static final String SEARCH_ENG=BASE_URL + "search_eng";

    public static final String SEARCH_COMMENT=BASE_URL + "search_comment";


    public static final String SEARCH_NEWS=BASE_URL + "new";
    /*获取签到记录*/
    public static final String GET_SIGNINS=BASE_URL + "get_signins";

}
