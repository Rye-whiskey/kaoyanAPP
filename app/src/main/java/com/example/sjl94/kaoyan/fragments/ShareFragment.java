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
import com.example.sjl94.kaoyan.bean.Share;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjl94 on 2018/4/2.
 */

public class ShareFragment extends Fragment{


    private List<Share> shareList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sharefragment, null);


        ListView listView = (ListView)view.findViewById(R.id.list_share);

        shareList = new ArrayList<Share>();
        for(int i = 1;i<10;i++)
        {
            Share bean  = new Share("赤井秀一","30分钟前","图书馆2楼右边","下午2点");
            shareList.add(bean);
        }

        ComAdapter mAdapter = new ComAdapter<Share>(getContext(),shareList,R.layout.ly_item_front){

            @Override
            public void convert(ViewHolder holder, Share item) {
                holder.setTextView(R.id.title,item.getTitle());
                holder.setTextView(R.id.time,item.getTime());
                holder.setTextView(R.id.wz,item.getContent());
                holder.setTextView(R.id.leavetime,item.getleavetime());
            }
        };



        listView.setAdapter(mAdapter);



        return view;
    }






}
