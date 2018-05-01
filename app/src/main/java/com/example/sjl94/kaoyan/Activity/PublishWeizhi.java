package com.example.sjl94.kaoyan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.utils.PreferencesUtils;
import com.example.sjl94.kaoyan.utils.StringUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjl94 on 2018/4/10.
 */

public class PublishWeizhi extends AppCompatActivity{
    private ButtonRectangle btn_fabu;

    private MaterialEditText title;

    private MaterialEditText key;

    private String str_username;
    private String str_title;

    private String str_key;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishweizhi);
        initView();
        initEvent();

    }
    private void initView(){
        btn_fabu = findViewById(R.id.btn_publish);
        title = findViewById(R.id.title);

        key = findViewById(R.id.key);
    }

    private void initEvent(){
        btn_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabu();
            }
        });
    }

    public void fabu(){
        str_username= PreferencesUtils.getString(PublishWeizhi.this,"username");
        str_title=title.getText().toString();

        str_key=key.getText().toString();
        if(StringUtils.isSpace(str_title)){
            Toast.makeText(PublishWeizhi.this,"请描述你的位置",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isSpace(str_key)){
            Toast.makeText(PublishWeizhi.this,"请描述离开时间",Toast.LENGTH_SHORT).show();
            return;
        }


        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username",str_username);
        params.put("weizhi", str_title);
        params.put("ltime",str_key);
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.PUBLISH_WEIZHI)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(PublishWeizhi.this,"发布成功",Toast.LENGTH_SHORT).show();
                    }
                });

        finish();
    }
}
