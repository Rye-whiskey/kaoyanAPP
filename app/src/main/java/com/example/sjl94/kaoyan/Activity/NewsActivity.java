package com.example.sjl94.kaoyan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.Comment;
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
 * Created by Administrator on 2018/5/2.
 */

public class NewsActivity extends Activity{
    private List<Comment> commentList;
    private String id;
    private String title;
    private String content;
    private TextView tv_title;
    private TextView tv_content;

    private Comment bean;
    private JsonParser parser;
    private JsonArray jsonArray;
    private MaterialEditText comment;

    private String str_username;
    private String str_comment;
    private Button btn_comment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = (ListView)findViewById(R.id.listview);
        tv_title=(TextView) findViewById(R.id.title);
        tv_content=(TextView)findViewById(R.id.content);
        comment = (MaterialEditText)findViewById(R.id.comment);
        btn_comment = (Button)findViewById(R.id.btn_comment);

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        id=bundle.getString("id");
        title=bundle.getString("title");
        content=bundle.getString("content");
        commentList = new ArrayList<Comment>();

        tv_title.setText(title);

        tv_content.setText(content);



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

                holder.setImageBitmap(NewsActivity.this,R.id.item_tx,item.getUsername());

            }
        };


        listView.setAdapter(mAdapter);

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_comment = comment.getText().toString();
                str_username = PreferencesUtils.getString(NewsActivity.this,"username");


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
                                Toast.makeText(NewsActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });



    }
}
