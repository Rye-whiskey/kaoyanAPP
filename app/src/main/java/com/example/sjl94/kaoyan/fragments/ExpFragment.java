package com.example.sjl94.kaoyan.fragments;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sjl94.kaoyan.Activity.PublishXinde;
import com.example.sjl94.kaoyan.MainActivity;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.adapter.TapFragmentPagerAdapter;
import com.example.sjl94.kaoyan.bean.Xinde;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjl94 on 2018/3/28.
 */

public class ExpFragment extends Fragment{

    private PopupWindow mMenuPop;
    private ImageView mImageView;

    private int PopWidth;
    private int PopHeight;




    private ViewPager viewPager;
    private List<Fragment> list;
    private TapFragmentPagerAdapter adapter;
    private Button btn_xinde;
    private Button btn_share;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view  = inflater.inflate(R.layout.activity_share,null);
        initView(view);

        list=new ArrayList<>();
        list.add(new XindeFragment());
        list.add(new ShareFragment());
        adapter=new TapFragmentPagerAdapter(getChildFragmentManager(),list);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


        return view;

    }

    private void initView(View view){


        mImageView = (ImageView)view.findViewById(R.id.iv_menu);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevitateMenu();
            }
        });



        btn_xinde= (Button)view.findViewById(R.id.btn_eng);
        btn_share = (Button)view.findViewById(R.id.btn_mat);
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

        btn_xinde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });







    }


    /**
     * 显示悬浮菜单
     */
    private void showLevitateMenu(){
        //动画
        mRotate(mImageView);

        //创建popwindow
        getPopMenu();

        //获取ImageView控件在手机屏幕的位置
        int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        int x = location[0];
        int y =location[1];

        /**
         * popwindow显示的位置
         * 参数一：基于某控件，一般在popupWindow.showAsDropDown()中比较有用，该处作用不大
         * 参数二：见名知意，写默认即可
         * 参数三：popupWindow在屏幕上显示位置的x坐标
         * 参数四：popupWindow在屏幕上显示位置的y左边
         */
        mMenuPop.showAtLocation(mImageView, Gravity.NO_GRAVITY, mImageView.getLeft()-PopWidth+mImageView.getWidth()/4, y+mImageView.getHeight()/2-PopHeight/2);

    }


    //当前旋转的度数
    private int rotate = 0;
    //每次旋转的度数
    private int rotation = 255;
    //判断顺时针转还是逆时针转
    private boolean rotateDirection = true;

    /**
     * 悬浮菜单动画效果
     *
     */
    private void mRotate(View v){
        ObjectAnimator animator;

        //判断是顺时针旋转还是逆时针旋转
        if(rotateDirection){
            animator = ObjectAnimator.ofFloat(v,"rotation",rotate,rotate-rotation);
            rotate = rotate+rotation;
        }else{
            animator = ObjectAnimator.ofFloat(v,"rotation",rotate,rotate+rotation);
            rotate = rotate-rotation;
        }
        //持续时间
        animator.setDuration(350);
        animator.start();
        rotateDirection=!rotateDirection;
    }

    /**
     * 获取Popupwindow实例分类
     *
     */
    private void getPopMenu(){
        if(null!=mMenuPop) {
            //动画
            mRotate(mImageView);
            mMenuPop.dismiss();
            mMenuPop = null;
            return;
        }else{
            //初始化popupwindow弹窗
            initMenuPop();

        }
    }

    /**
     * 初始化popwindow
     */
    private void initMenuPop(){
        //获取自定义布局文件pop.xml的视图
        View view = View.inflate(getActivity(), R.layout.item_pop_levitate_menu, null);
        //测量view的宽高，由于popupwindow没有测量方法，只能测量内部view的宽高
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        PopWidth = view.getMeasuredWidth();
        PopHeight = view.getMeasuredHeight();

        //下面这两个必须有！！
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        // PopupWindow(布局，宽度，高度) 注意，此处宽高应为-2也就是wrap_content
        mMenuPop = new PopupWindow(view, -2, -2, true);


        // 重写onKeyListener,按返回键消失
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    mRotate(mImageView);
                    mMenuPop.dismiss();
                    mMenuPop = null;
                    return true;
                }
                return false;
            }
        });


        //点击其他地方消失
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mMenuPop != null && mMenuPop.isShowing()) {
                    mRotate(mImageView);
                    mMenuPop.dismiss();
                    mMenuPop = null;
                    return true;
                }
                return false;
            }
        });




        final TextView tv_newClue = (TextView) view.findViewById(R.id.tv_newClue);
        final TextView tv_Edit = (TextView) view.findViewById(R.id.tv_edit);

        //新建线索
        tv_newClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"点击了"+tv_newClue.getText().toString(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), PublishXinde.class);
                startActivity(intent);

                mRotate(mImageView);
                mMenuPop.dismiss();
                mMenuPop = null;

            }
        });

        //编辑
        tv_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"点击了"+tv_Edit.getText().toString(), Toast.LENGTH_SHORT).show();

                mRotate(mImageView);
                mMenuPop.dismiss();
                mMenuPop = null;

            }
        });

    }







}
