package com.example.sjl94.kaoyan.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.MainActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.Comment;
import com.example.sjl94.kaoyan.bean.Xinde;
import com.example.sjl94.kaoyan.utils.JsonUtils;
import com.example.sjl94.kaoyan.utils.PreferencesUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class XindeActivity extends Activity {
    private List<Comment> commentList;
    private String id;
    private String title;
    private String time;
    private String content;
    private String key;
    private String username;

    private ImageView tx;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_key;
    private TextView tv_content;


    private Comment bean;
    private JsonParser parser;
    private JsonArray jsonArray;
    private MaterialEditText comment;
    private String str_comment;
    private String str_username;
    private Button btn_comment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinde);

        ListView listView = (ListView)findViewById(R.id.listview);
        tx=(ImageView) findViewById(R.id.tx);
        tv_title=(TextView) findViewById(R.id.title);
        tv_time=(TextView)findViewById(R.id.time) ;
        tv_key=(TextView)findViewById(R.id.key);
        tv_content=(TextView)findViewById(R.id.content);


        comment = (MaterialEditText)findViewById(R.id.comment);
        btn_comment = (Button)findViewById(R.id.btn_comment);

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        id=bundle.getString("id");
        title=bundle.getString("title");
        time=bundle.getString("time");
        content=bundle.getString("content");
        key=bundle.getString("key");
        username=bundle.getString("username");
        commentList = new ArrayList<Comment>();


        tv_title.setText(title);
        tv_time.setText(time);
        tv_content.setText(content);
        tv_key.setText(key);


        Glide.with(XindeActivity.this)
                .load("http://193.112.122.190:3000/public/images/"+username+".png")
                .into(tx);


           /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.SEARCH_COMMENT)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {


                        parser = new JsonParser();
                        jsonArray = parser.parse(s).getAsJsonArray();
                        for(JsonElement xinde:jsonArray){
                            String x=xinde.toString();
                            bean=new Comment();
                            bean = JsonUtils.fromJson(x,Comment.class);
                            commentList.add(bean);
                        }

                    }
                });

        bean=new Comment();
        commentList.add(bean);






        ComAdapter mAdapter = new ComAdapter<Comment>(getBaseContext(),commentList,R.layout.item_comment){

            @Override
            public void convert(ViewHolder holder, Comment item) {
                holder.setTextView(R.id.title,item.getUsername());
                holder.setTextView(R.id.time,item.getTime());
                holder.setTextView(R.id.content,item.getComment());

                holder.setImageBitmap(XindeActivity.this,R.id.item_tx,item.getUsername());

            }
        };


        listView.setAdapter(mAdapter);

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_comment = comment.getText().toString();
                str_username = PreferencesUtils.getString(XindeActivity.this,"username");


                /*构造请求体*/
                HashMap<String, String> params = new HashMap<>();
                params.put("username",str_username);
                params.put("comment", str_comment);
                params.put("id", id);
                JSONObject jsonObject = new JSONObject(params);

                OkGo.post(Api.PUBLISH_COMMENT)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(XindeActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });






    }


}
