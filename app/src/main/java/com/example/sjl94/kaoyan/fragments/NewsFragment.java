package com.example.sjl94.kaoyan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjl94 on 2018/3/28.
 */

public class NewsFragment extends Fragment {


    private Banner mBanner;
    String[] images = new String[]{
            "http://www.baidu.com/123.png",
            "http://www.baidu.com/123.png",
            "http://www.baidu.com/123.png",
            "http://www.baidu.com/123.png"
    };

    String[] tips=new String[] {
            "我的日记",
            "我的周记",
            "我的感悟",
            "我的生活" };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_zixun, null);


        // 加载图片和标题内容
        List imageList = new ArrayList();
        final List titleList = new ArrayList();
        for (int i = 0; i < images.length; i++) {
            imageList.add(images[i]);
            titleList.add(tips[i]);
        }

        mBanner = (Banner)view.findViewById(R.id.banner_recent);
        //设置加载器
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });


        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);//设置页面与标题
        mBanner.setDelayTime(3000);//设置轮播时间
        mBanner.setImages(imageList);//设置图片源
        mBanner.setBannerTitles(titleList);//设置标题
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch(position){
                    case 0:
                        ToastUtil.toast(getActivity(),titleList.get(0).toString());
                        break;
                    case 1:
                        ToastUtil.toast(getActivity(),titleList.get(1).toString());
                        break;
                    case 2:
                        ToastUtil.toast(getActivity(),titleList.get(2).toString());
                        break;
                    case 3:
                        ToastUtil.toast(getActivity(),titleList.get(3).toString());
                        break;
                    default:
                        break;

                }
            }
        });



        return view;
    }

}
