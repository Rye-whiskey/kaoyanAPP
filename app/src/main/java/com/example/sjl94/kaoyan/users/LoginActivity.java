package com.example.sjl94.kaoyan.users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.MainActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.Image;
import com.example.sjl94.kaoyan.bean.ResponseLogin;
import com.example.sjl94.kaoyan.utils.Base64Utils;
import com.example.sjl94.kaoyan.utils.Constant;
import com.example.sjl94.kaoyan.utils.JsonUtils;
import com.example.sjl94.kaoyan.utils.NetworkUtils;
import com.example.sjl94.kaoyan.utils.PreferencesUtils;
import com.example.sjl94.kaoyan.utils.StringUtils;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
//import com.lzy.okgo.adapter.Call;

//import com.lzy.okgo.model.Response;
//import com.lzy.okgo.model.Response;

import org.json.JSONObject;



import java.util.HashMap;
import java.util.TooManyListenersException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjl94 on 2018/3/27.
 */

public class LoginActivity extends Activity {
    private ButtonRectangle btn_login;
    private MaterialEditText username;
    private MaterialEditText password;
    private ImageView imageView;
    private String str_username;
    private String str_password;
    private Dialog md;
    private ResponseLogin login;
    private Image image;
    private ButtonFlat btn_toRegister;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView(){
        imageView=(ImageView)findViewById(R.id.tx);
        btn_login=(ButtonRectangle) findViewById(R.id.btn_login);
        username=(MaterialEditText)findViewById(R.id.username);
        password=(MaterialEditText)findViewById(R.id.password);
        btn_toRegister=(ButtonFlat) findViewById(R.id.btn_toRegister);

        /*如果SP里面保存了用户名密码，就将它取出来*/
        str_username= PreferencesUtils.getString(LoginActivity.this,"username");
        str_password=PreferencesUtils.getString(LoginActivity.this,"password");

        if(!StringUtils.isEmpty(str_username)){
            username.setText(str_username);
            password.setText(str_password);
        }

    }

    private void initEvent(){
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Glide.with(LoginActivity.this)
                        .load("http://193.112.122.190:3000/public/images/"+username.getText()+".png")
                        .into(imageView);

            }
        });
        //登录
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkUser();

            }

        });
        //注册
        btn_toRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                toActivity(RegisterActivity.class);
            }
        });

    }

    //用户名和密码校验
    public void checkUser(){
        //判断网络是否连接
        if(!NetworkUtils.isConnected(LoginActivity.this)){
            Toast.makeText(LoginActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
            return;
        }

        str_username=username.getText().toString();
        str_password=password.getText().toString();

        if(StringUtils.isSpace(str_username)){
            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isSpace(str_password)){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //显示提示正在登录的对话框

        /**
         *
         */


        //构造请求体
        HashMap<String,String> params=new HashMap<>();
        params.put("username",str_username);
        params.put("password",str_password);
        JSONObject jsonObject = new JSONObject(params);
        //发送登录请求

        OkGo.post(Api.LOGIN)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback(){


                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);

                        if(login.getStatus().equals(Constant.SUCCESS)){


                            PreferencesUtils.putString(LoginActivity.this,"username",str_username);
                            PreferencesUtils.putString(LoginActivity.this,"password",str_password);

                            toActivity(MainActivity.class);
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            LoginActivity.this.finish();
                        }else{
                            if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                                Toast.makeText(LoginActivity.this,"系统错误",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_USERNAME)){
                                Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_PASSWORD)){
                                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });


    }
    //页面跳转
    public void toActivity(Class<?> cla){
        Intent intent=new Intent(this,cla);

        startActivity(intent);
    }


}
