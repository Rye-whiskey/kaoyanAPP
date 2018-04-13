package com.example.sjl94.kaoyan.bean;

import java.io.Serializable;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class Xinde implements Serializable{
    private String username;
    private String title;
    private String createTime;
    private String content;
    private String key;


    public String getUsername(){
        return username;
    }
    public String getTitle(){
        return title;
    }
    public String getCreateTime(){
        return createTime;
    }
    public String getContent(){
        return content;
    }
    public String getKey(){
        return key;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setCreateTime(String time){
        this.createTime=createTime;
    }
    public void setContent(String content){
        this.content=content;
    }
    public void setKey(String key){
        this.key=key;
    }

}
