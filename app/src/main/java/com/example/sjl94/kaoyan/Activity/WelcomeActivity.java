package com.example.sjl94.kaoyan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.sjl94.kaoyan.MainActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.users.LoginActivity;
import com.example.sjl94.kaoyan.users.UserManage;


/**
 * Created by sjl94 on 2018/4/7.
 */

public class WelcomeActivity extends AppCompatActivity {
    private static final int GO_HOME=0;//去主页
    private static final int GO_LOGIN =1;//去登录页

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);
        if(UserManage.getInstance().hasUserInfo(this)){
            handler.sendEmptyMessageDelayed(GO_HOME,2000);

        }else{
            handler.sendEmptyMessageAtTime(GO_LOGIN,2000);
        }

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case GO_HOME:
                    Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN:
                    Intent intent1 = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };









}
