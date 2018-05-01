package com.example.sjl94.kaoyan;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.utils.PreferencesUtils;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, int resLayoutId, ViewGroup parent){
        this.mViews=new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        this.mConvertView.setTag(this);
    }


    //获取一个ViewHolder
    public static ViewHolder getHolder(Context context, int resLayoutId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, resLayoutId, parent);
        }
        return (ViewHolder) convertView.getTag();
    }


    //通过控件的id获取对应的控件，如果没有则加入mViews;记住 <T extends View> T 这种用法
    public <T extends View> T getItemView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }




    //获得一个convertView
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView赋值
     */
    public ViewHolder setTextView(int viewId, String text) {
        TextView view = getItemView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView赋值——drawableId
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getItemView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView赋值——bitmap
     */
    public ViewHolder setImageBitmap(Activity activity,int viewId, String username) {
        ImageView view = getItemView(viewId);

        Glide.with(activity)
                .load("http://193.112.122.190:3000/public/images/"+ username+".png")
                .into(view);


        return this;
    }


}
