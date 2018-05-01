package com.example.sjl94.kaoyan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sjl94.kaoyan.R;

import java.util.ArrayList;

/**
 * Created by sjl94 on 2018/4/16.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private ArrayList<String> mData;

    public WordAdapter(ArrayList<String> data){
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item,parent,false);
        //实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView){
            super(itemView);


        }
    }
}
