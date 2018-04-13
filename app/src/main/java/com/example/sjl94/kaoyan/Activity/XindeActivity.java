package com.example.sjl94.kaoyan.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class XindeActivity extends Activity {
    private List<Comment> commentList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinde);

        ListView listView = (ListView)findViewById(R.id.listview);

        commentList = new ArrayList<Comment>();
        for(int i=1;i<10;i++){
            Comment bean = new Comment("sjl"+i,"30分钟前","好好学习，天天向上");
            commentList.add(bean);
        }

        ComAdapter mAdapter = new ComAdapter<Comment>(getBaseContext(),commentList,R.layout.item_comment){

            @Override
            public void convert(ViewHolder holder, Comment item) {
                holder.setTextView(R.id.title,item.getUsername());
                holder.setTextView(R.id.time,item.getTime());
                holder.setTextView(R.id.content,item.getContent());

            }
        };


        listView.setAdapter(mAdapter);






    }
}
