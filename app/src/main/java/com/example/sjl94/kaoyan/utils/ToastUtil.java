package com.example.sjl94.kaoyan.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sjl94 on 2018/3/31.
 */

public class ToastUtil {
    private static Toast mToast;

    public static void toast(Context context, String strings) {
        if (mToast == null) {
            mToast = Toast.makeText(context, strings, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(strings);
        }
        mToast.show();
    }
}
