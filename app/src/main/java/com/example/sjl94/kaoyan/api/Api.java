package com.example.sjl94.kaoyan.api;

/**
 * Created by sjl94 on 2018/3/27.
 */

public class Api {
    /*配置baseUl*/
    public static final String BASE_URL="http://172.16.60.25:3000/";
    /*用户登录*/
    public static final String LOGIN=BASE_URL + "login";
    /*用户注册*/
    public static final String REGISTER=BASE_URL + "register";
    /*添加请假记录*/
    public static final String PUBLISH=BASE_URL + "publish";
    /*获得用户请求记录*/
    public static final String SEARCH=BASE_URL + "search";


    public static final String SEARCH_MATH=BASE_URL + "search_math";


    public static final String SEARCH_ENG=BASE_URL + "search_eng";
    /*用户签到*/
    public static final String GET_IMAGE=BASE_URL + "get_image";
    /*获取签到记录*/
    public static final String GET_SIGNINS=BASE_URL + "get_signins";

}
