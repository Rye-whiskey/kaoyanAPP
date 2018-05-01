package com.example.sjl94.kaoyan.Activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.RecyclerViewClickListener;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.adapter.WordAdapter;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.Word;
import com.example.sjl94.kaoyan.bean.Xinde;
import com.example.sjl94.kaoyan.utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;



/**
 * Created by sjl94 on 2018/4/16.
 */

public class WordActivity extends AppCompatActivity{



    private List<Word> wordList;
    private JsonParser parser;
    private JsonArray jsonArray;
    private Word bean;
    private ListView recyclerView;

    private ComAdapter adapter;



    private RecyclerView.LayoutManager layoutManager;







    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        initView();






//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//
//                Toast.makeText(WordActivity.this,"aaaaaaaaaaa",Toast.LENGTH_SHORT).show();
//                switch (e.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        mPosX = e.getX();
//                        mPosY = e.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        mCurPosX = e.getX();
//                        mCurPosY = e.getY();
//
//                        break;
//                    case MotionEvent.ACTION_UP:
////                        if (mCurPosY - mPosY > 0
////                                && (Math.abs(mCurPosY - mPosY) > 25)) {
////                            //向下滑動
////                            tiShi(mContext,"向下");
////
////                        } else if (mCurPosY - mPosY < 0
////                                && (Math.abs(mCurPosY - mPosY) > 25)) {
////                            //向上滑动
////                            tiShi(mContext,"向上");
////                        }
//                        if (mCurPosX - mPosX > 0
//                                && (Math.abs(mCurPosX - mPosX) > 25)) {
//                            flipCard();
//                            //向左滑動
////                            tiShi(mContext,"向左");
//
//                        } else if (mCurPosX - mPosX < 0
//                                && (Math.abs(mCurPosX - mPosX) > 25)) {
//                            //向右滑动
//                            flipCard();
////                            tiShi(mContext,"向右");
//                        }
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });




    }















    private void initView(){
        recyclerView = (ListView) findViewById(R.id.recycler_view);


        wordList =new ArrayList<Word>();

               /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "英语");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.VAC)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parser = new JsonParser();
                        jsonArray = parser.parse(s).getAsJsonArray();
                        for(JsonElement xinde:jsonArray){
                            String x=xinde.toString();
                            bean=new Word();
                            bean = JsonUtils.fromJson(x,Word.class);
                            wordList.add(bean);
                        }

                    }
                });

        bean=new Word();
        wordList.add(bean);


        ComAdapter mAdapter = new ComAdapter<Word>(this, wordList,R.layout.word_item) {
            @Override
            public void convert(ViewHolder holder, Word item) {
                holder.setTextView(R.id.vacname,item.getName());
                holder.setTextView(R.id.vacmeans,item.getMeans());

            }
        };

        recyclerView.setAdapter(mAdapter);
    }



}
