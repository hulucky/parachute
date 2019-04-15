package com.parachute.Tools;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parachute.bean.PressBean;

import java.util.List;

/**
 * Date: 2018/3/28  10:05
 * Description: 常用方法类
 */

public class Method {

    //跳转Activity
    public static void toActivity(@NonNull Activity activity , @NonNull Class<?> targetClass){
        Intent intent = new Intent();
        intent.setClass(activity, targetClass);
        //根据版本设置跳转方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }


    /**
     * function ：关闭软键盘
     */
    public static void closeKey(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
    /**
     * 判空
     *
     * @param editText
     * @return
     */
    public static boolean IsEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            return true;
        }
        return false;
    }

    public static float[] AvePress(List<PressBean> mpressbean)
    {
        float[] mres={0f,0f};
        float sumjy=0f,sumcy=0f;
        for (PressBean mbean:mpressbean
             ) {
            sumjy=sumjy+Float.parseFloat(mbean.getStr0());
            sumcy=sumcy+    Float.parseFloat(mbean.getStr1());
        }
        mres[0]=sumjy/mpressbean.size();
        mres[1]=sumcy/mpressbean.size();
        return mres;
    }

}
