package com.example.sjl94.kaoyan.users;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.SDCardUtils;
import com.example.sjl94.kaoyan.Activity.PublishXinde;
import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.api.Api;
import com.example.sjl94.kaoyan.bean.ResponseLogin;
import com.example.sjl94.kaoyan.utils.Base64Utils;
import com.example.sjl94.kaoyan.utils.BitmapUtil;
import com.example.sjl94.kaoyan.utils.Constant;
import com.example.sjl94.kaoyan.utils.JsonUtils;
import com.example.sjl94.kaoyan.utils.NetworkUtils;
import com.example.sjl94.kaoyan.utils.StringUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sjl94 on 2018/3/27.
 */

public class RegisterActivity extends AppCompatActivity{
    private ButtonRectangle btn_register;
    private MaterialEditText username_register;
    private MaterialEditText password_register;
    private String str_username;
    private String str_password;
    private ResponseLogin login;
    private ImageView tx;
    private PopupWindow pop;
    private Bitmap bitmap ;//存放裁剪后的头像
    private String fileName;//头像名称
    private String picturePath;//头像路径
    private String icon;


    private static final int CAMERA_REQUEST_CODE = 1;//拍照返回码
    private static final int GALLERY_REQUEST_CODE = 2;//相册返回码

    private static final int RESULT_OPEN_IMAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView(){
        btn_register=(ButtonRectangle) findViewById(R.id.btn_register);
        username_register=(MaterialEditText)findViewById(R.id.username_register);
        password_register=(MaterialEditText)findViewById(R.id.password_register);
        tx=(ImageView)findViewById(R.id.tx);
    }

    private void initEvent(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser();
            }
        });
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegisterActivity.this,"点击了",Toast.LENGTH_SHORT).show();
                shouPopupWindow();
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CAMERA_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){

            Uri uri = data.getData();
            if(uri != null){
                Cursor cursor = this.getContentResolver().query(uri,null,null,null,null);

                if(cursor.moveToFirst()) {
                    picturePath = cursor.getString(cursor.getColumnIndex("_data"));
                    fileName = getBitmapName(picturePath);

                    bitmap = BitmapFactory.decodeFile(picturePath);
                    tx.setImageBitmap(bitmap);
                }

                }else{
                Toast.makeText(this,"保存照片失败",Toast.LENGTH_SHORT).show();
                return;
                }
            }

            if(requestCode==GALLERY_REQUEST_CODE&&resultCode==RESULT_OK&&null!=data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            fileName = getBitmapName(picturePath);
            cursor.close();

            if(SDCardUtils.isSDCardEnable()){
                bitmap = BitmapFactory.decodeFile(picturePath);
                bitmap = BitmapUtil.toRoundBitmap(bitmap);
                icon = Base64Utils.bitmapToString(bitmap);

                tx.setImageBitmap(bitmap);
            }else{
                return;
            }

        }else{
                return;
            }


    }


    //获取图片的名称
    public String getBitmapName(String picPath){
        String bitmapName="";
        String[]  s = picPath.split("/");
        bitmapName = s[s.length-1];
        return bitmapName;
    }


    public void shouPopupWindow(){
        pop = new PopupWindow(RegisterActivity.this);
        View view = getLayoutInflater().inflate(R.layout.pop_select_photo,null);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);

        final TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        final TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);

        //新建线索
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this,"点击了"+tv_select_camera.getText().toString(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CAMERA_REQUEST_CODE);


                pop.dismiss();
                pop = null;

            }
        });

        //编辑
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this,"点击了"+tv_select_gallery.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent ii = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ii,GALLERY_REQUEST_CODE);

                pop.dismiss();
                pop = null;

            }
        });




    }


    //用户名密码校验
    public void checkUser(){
        //判断网络是否连接
        if(!NetworkUtils.isConnected(RegisterActivity.this)){
            Toast.makeText(RegisterActivity.this,"请连接网络",Toast.LENGTH_SHORT).show();
            return;
        }
        str_username=username_register.getText().toString();
        str_password=password_register.getText().toString();
        if(StringUtils.isSpace(str_username)){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isSpace(str_password)){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }


         /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("image",icon);

        params.put("username", str_username);
        params.put("password", str_password);
        JSONObject jsonObject = new JSONObject(params);

        OkGo.post(Api.REGISTER)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);

                        if(login.getStatus().equals(Constant.SUCCESS)){
                            RegisterActivity.this.finish();
                            Toast.makeText(RegisterActivity.this,"注册成功，请登录",Toast.LENGTH_SHORT).show();
                        }else{
                            if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                                Toast.makeText(RegisterActivity.this,"系统错误",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_USER_EXIST)){
                                Toast.makeText(RegisterActivity.this,"用户名已被注册",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });


    }



}
