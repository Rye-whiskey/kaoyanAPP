package com.example.sjl94.kaoyan;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.example.sjl94.kaoyan.adapter.TapFragmentPagerAdapter;
import com.example.sjl94.kaoyan.fragments.ContentFragment;
import com.example.sjl94.kaoyan.fragments.ExpFragment;
import com.example.sjl94.kaoyan.fragments.NewsFragment;
import com.example.sjl94.kaoyan.fragments.StudyFragment;
import com.example.sjl94.kaoyan.users.LoginActivity;
import com.example.sjl94.kaoyan.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar navigationBar;
    private BottomNavigationItem msgItem;
    private BottomNavigationItem zixunItem;
    private BottomNavigationItem xindeItem;
    private ImageView imageView;
    private View headerLayout;
    private TextView username;
    //侧拉栏




    private ViewPager viewPager;
    private List<Fragment> list;
    private TapFragmentPagerAdapter adapter;



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
        headerLayout=navigationView.inflateHeaderView(R.layout.drawer_header);
        imageView=(ImageView)headerLayout.findViewById(R.id.tx);
        username=(TextView)headerLayout.findViewById(R.id.username);
        username.setText(PreferencesUtils.getString(MainActivity.this,"username"));
        Glide.with(MainActivity.this)
                .load("http://193.112.122.190:3000/public/images/"+"sdf.png")
                .into(imageView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:

                        toActivity(LoginActivity.class);
                }
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
    //页面跳转
    public void toActivity(Class<?> cla){
        Intent intent=new Intent(this,cla);
        startActivity(intent);
    }

}
