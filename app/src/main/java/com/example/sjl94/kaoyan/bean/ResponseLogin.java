package com.example.sjl94.kaoyan.bean;

import java.io.Serializable;

/**
 * Created by sjl94 on 2018/3/27.
 */

public class ResponseLogin implements Serializable{
    private String status;
    private String msg;
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return status;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }

    public String getMsg(){
        return msg;
    }


}
