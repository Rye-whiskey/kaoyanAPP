package com.example.sjl94.kaoyan.bean;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class Comment {

    private String username;
    private String time;
    private String content;

    public Comment(String username, String time, String content){
        this.username=username;
        this.time=time;
        this.content=content;

    }

    public String getUsername(){
        return username;
    }
    public String getTime(){
        return time;
    }
    public String getContent(){
        return content;
    }


}
