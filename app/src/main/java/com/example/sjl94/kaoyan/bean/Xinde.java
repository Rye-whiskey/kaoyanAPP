package com.example.sjl94.kaoyan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class Xinde implements Serializable{
    private String username;
    private String title;
    private String createTime;
    private String content;
    private String key;
    private String _id;


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
    public String get_id(){
        return _id;
    }
    public void set_id(String _id){
        this._id=_id;
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
