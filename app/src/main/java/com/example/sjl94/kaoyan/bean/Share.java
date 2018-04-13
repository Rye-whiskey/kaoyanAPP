package com.example.sjl94.kaoyan.bean;

/**
 * Created by sjl94 on 2018/4/3.
 */

public class Share {
    private String title;
    private String time;
    private String wz;
    private String leavetime;
    public Share(String title, String time, String wz, String leavetime){
        this.title=title;
        this.time=time;
        this.wz=wz;
        this.leavetime=leavetime;
    }

    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getContent(){
        return wz;
    }
    public String getleavetime(){
        return leavetime;
    }
}
