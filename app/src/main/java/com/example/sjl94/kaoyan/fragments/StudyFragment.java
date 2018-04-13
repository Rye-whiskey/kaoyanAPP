package com.example.sjl94.kaoyan.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.adapter.TapFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjl94 on 2018/3/28.
 */

public class StudyFragment extends Fragment {


    private ViewPager viewPager;
    private List<Fragment> list;
    private TapFragmentPagerAdapter adapter;
    private Button btn_eng;
    private Button btn_math;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.study_frag,null);


        initView(view);

        list=new ArrayList<>();
        list.add(new EngFragment());
        list.add(new MathFragment());
        adapter = new TapFragmentPagerAdapter(getChildFragmentManager(),list);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


        return view;
    }


    private void initView(View view){
        btn_eng=(Button)view.findViewById(R.id.btn_eng);
        btn_math=(Button)view.findViewById(R.id.btn_mat);
       viewPager=(ViewPager)view.findViewById(R.id.study_fragment);
       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

       btn_eng.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               viewPager.setCurrentItem(0);
           }
       });

       btn_math.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               viewPager.setCurrentItem(1);
           }
       });
    }
}
