package com.example.sjl94.kaoyan;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.sjl94.kaoyan.adapter.TapFragmentPagerAdapter;
import com.example.sjl94.kaoyan.fragments.ContentFragment;
import com.example.sjl94.kaoyan.fragments.ExpFragment;
import com.example.sjl94.kaoyan.fragments.NewsFragment;
import com.example.sjl94.kaoyan.fragments.StudyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar navigationBar;
    private BottomNavigationItem msgItem;
    private BottomNavigationItem zixunItem;
    private BottomNavigationItem xindeItem;

    //侧拉栏




    private ViewPager viewPager;
    private List<Fragment> list;
    private TapFragmentPagerAdapter adapter;

    private Fragment msgFrag;
    private Fragment zixunFrag;
    private Fragment xindeFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        list=new ArrayList<>();
        list.add(new StudyFragment());
        list.add(new NewsFragment());
        list.add(new ExpFragment());
        adapter = new TapFragmentPagerAdapter(getSupportFragmentManager(), list);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
    private void initView(){
        navigationBar=(BottomNavigationBar)findViewById(R.id.navigationBar);
        viewPager=(ViewPager)findViewById(R.id.viewpager) ;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    switch (position){
                        case 0:
                            navigationBar.selectTab(0);
                            break;
                        case 1:
                            navigationBar.selectTab(1);
                            break;
                        case 2:
                            navigationBar.selectTab(2);
                            break;
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setNavigationBar();


        final NavigationView navigationView =(NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });




    }
    private void setNavigationBar(){
        navigationBar.setAutoHideEnabled(true);//自动隐藏

        //BottomNavigationBar.MODE_SHIFTING;
        //BottomNavigationBar.MODE_FIXED;
        //BottomNavigationBar.MODE_DEFAULT;
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        // BottomNavigationBar.BACKGROUND_STYLE_DEFAULT;
        // BottomNavigationBar.BACKGROUND_STYLE_RIPPLE
        // BottomNavigationBar.BACKGROUND_STYLE_STATIC
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);


        navigationBar.setBarBackgroundColor(R.color.white);//背景颜色
        navigationBar.setInActiveColor(R.color.gray);//未选中时的颜色
        navigationBar.setActiveColor(R.color.colorPrimaryDark);//选中时的颜色

        msgItem = new BottomNavigationItem(R.drawable.xuexi,"学习");
        zixunItem = new BottomNavigationItem(R.drawable.xinwen,"资讯");
        xindeItem = new BottomNavigationItem(R.drawable.zixun,"心得");

        navigationBar.addItem(msgItem).addItem(zixunItem).addItem(xindeItem).initialise();

        navigationBar.setTabSelectedListener(this);


    }






    @Override
    public void onTabSelected(int position) {
        //hideAllFrag();
        switch (position){
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                break;

            case 2:
                viewPager.setCurrentItem(2);
                break;


        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void hideAllFrag(){

    }
}
