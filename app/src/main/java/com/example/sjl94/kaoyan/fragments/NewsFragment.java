package com.example.sjl94.kaoyan.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.Activity.NewsActivity;
import com.example.sjl94.kaoyan.Activity.XindeActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.ViewHolder;
import com.example.sjl94.kaoyan.adapter.ComAdapter;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.News;
import com.example.sjl94.kaoyan.bean.Xinde;
import com.example.sjl94.kaoyan.utils.JsonUtils;
import com.example.sjl94.kaoyan.utils.TimeUtil;
import com.example.sjl94.kaoyan.utils.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjl94 on 2018/3/28.
 */

public class NewsFragment extends Fragment {

    private News bean;
    private List<News> newsList;
    private JsonParser parser;
    private JsonArray jsonArray;

    private Banner mBanner;
    String[] images = new String[]{
            "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png",
            "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png",
            "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png",
            "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png"
    };

    String[] tips=new String[] {
            "我的日记",
            "我的周记",
            "我的感悟",
            "我的生活" };





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_zixun, null);

        ListView listView = (ListView)view.findViewById(R.id.list_zixun);

        newsList = new ArrayList<News>();


        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "123");
        params.put("password", "321");
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.SEARCH_NEWS)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        parser = new JsonParser();
                        jsonArray = parser.parse(s).getAsJsonArray();
                        for(JsonElement xinde:jsonArray){
                            String x=xinde.toString();
                            bean=new News();
                            bean = JsonUtils.fromJson(x,News.class);
                            newsList.add(bean);
                        }

                    }
                });

        bean=new News();
        newsList.add(bean);

        ComAdapter mAdapter = new ComAdapter<News>(getContext(), newsList,R.layout.zixun_item) {
            @Override
            public void convert(ViewHolder holder, News item) {
                holder.setTextView(R.id.base_swipe_item_title,item.getTitle());

            }
        };

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id=newsList.get(i).get_id();
                String title=newsList.get(i).getTitle();
                String content=newsList.get(i).getContent();
                Intent intent=new Intent(getActivity(),NewsActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                startActivity(intent);
            }
        });




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
