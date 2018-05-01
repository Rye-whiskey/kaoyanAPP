package com.example.sjl94.kaoyan.fragments;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.Share;
import com.example.sjl94.kaoyan.bean.Weizhi;
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
 * Created by sjl94 on 2018/4/2.
 */

public class ShareFragment extends Fragment{

    private Weizhi bean;
    private List<Weizhi> shareList;
    private JsonParser parser;
    private JsonArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sharefragment, null);


        ListView listView = (ListView)view.findViewById(R.id.list_share);

        shareList = new ArrayList<Weizhi>();

        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "123");
        params.put("password", "321");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.SEARCH_WEIZHI)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parser = new JsonParser();
                        jsonArray = parser.parse(s).getAsJsonArray();
                        for(JsonElement xinde:jsonArray){
                            String x=xinde.toString();
                            bean=new Weizhi();
                            bean = JsonUtils.fromJson(x,Weizhi.class);
                            shareList.add(bean);
                        }

                    }
                });

        bean=new Weizhi();
        shareList.add(bean);


        ComAdapter mAdapter = new ComAdapter<Weizhi>(getContext(),shareList,R.layout.weizhi_item){

            @Override
            public void convert(ViewHolder holder, Weizhi item) {
                holder.setTextView(R.id.title,item.getUsername());
                holder.setTextView(R.id.wz,item.getWeizhi());
                holder.setTextView(R.id.time,item.getCreateTime());

                holder.setTextView(R.id.leavetime,item.getLtime());
                holder.setImageBitmap(getActivity(),R.id.tx,"sdf");
            }
        };



        listView.setAdapter(mAdapter);



        return view;
    }






}
