package com.example.sjl94.kaoyan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sjl94.kaoyan.R;
import com.example.sjl94.kaoyan.htmledittext.HtmlEditText;

/**
 * Created by sjl94 on 2018/4/22.
 */

public class MathActivity extends AppCompatActivity implements HtmlEditText.OnChoosePicListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

    }

    @Override
    public void onChoose() {

    }
}
