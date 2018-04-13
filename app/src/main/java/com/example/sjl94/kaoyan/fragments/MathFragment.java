package com.example.sjl94.kaoyan.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sjl94.kaoyan.Activity.XindeActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.api.Api;
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
 * Created by sjl94 on 2018/3/31.
 */

public class MathFragment extends Fragment {

    private List<Xinde> xindeList;
    private JsonParser parser;
    private JsonArray jsonArray;
    private Xinde bean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.math_frag,null);


        ListView listView = (ListView)view.findViewById(R.id.list_eng);

        xindeList =new ArrayList<Xinde>();




               /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "数学");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.SEARCH_MATH)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parser = new JsonParser();
                        jsonArray = parser.parse(s).getAsJsonArray();
                        for(JsonElement xinde:jsonArray){
                            String x=xinde.toString();
                            bean=new Xinde();
                            bean = JsonUtils.fromJson(x,Xinde.class);
                            xindeList.add(bean);
                        }

                    }
                });

        bean=new Xinde();
        xindeList.add(bean);





        ComAdapter mAdapter = new ComAdapter<Xinde>(getContext(), xindeList,R.layout.xinde_item) {
            @Override
            public void convert(ViewHolder holder, Xinde item) {
                holder.setTextView(R.id.item_title,item.getTitle());
                holder.setTextView(R.id.item_time,item.getCreateTime());
                holder.setTextView(R.id.content,item.getContent());
                holder.setTextView(R.id.item_key,item.getKey());
            }
        };

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toActivity(XindeActivity.class);
            }
        });

        return view;
    }

    //页面跳转
    public void toActivity(Class<?> cla){
        Intent intent=new Intent(getActivity(),cla);
        startActivity(intent);
    }
}
