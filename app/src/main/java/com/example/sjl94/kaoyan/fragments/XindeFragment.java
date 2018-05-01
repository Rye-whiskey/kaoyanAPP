package com.example.sjl94.kaoyan.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.sjl94.kaoyan.utils.TimeUtil;
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
 * Created by sjl94 on 2018/4/1.
 */

public class XindeFragment extends Fragment {
    private Xinde bean;
    private List<Xinde> xindeList;
    private JsonParser parser;
    private JsonArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.exp_fragment,null);

        ListView listView = (ListView)view.findViewById(R.id.xinde);

        xindeList =new ArrayList<Xinde>();


        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "123");
        params.put("password", "321");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.SEARCH)
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
                if(item.getCreateTime()!=null)
                holder.setTextView(R.id.item_time, TimeUtil.GTMToLocal(item.getCreateTime()));
                holder.setTextView(R.id.content,item.getContent());
                holder.setImageBitmap(getActivity(),R.id.item_tx,item.getUsername());
                holder.setTextView(R.id.item_key,item.getKey());
            }
        };

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id=xindeList.get(i).get_id();
                String title=xindeList.get(i).getTitle();
                String time=xindeList.get(i).getCreateTime();
                String content=xindeList.get(i).getContent();
                String key=xindeList.get(i).getKey();
                String username=xindeList.get(i).getUsername();
                Intent intent=new Intent(getActivity(),XindeActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("time",time);
                intent.putExtra("content",content);
                intent.putExtra("key",key);
                intent.putExtra("username",username);

                startActivity(intent);

            }
        });


        return view;
    }



}
