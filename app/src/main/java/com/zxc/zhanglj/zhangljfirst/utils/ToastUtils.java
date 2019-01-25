package com.zxc.zhanglj.zhangljfirst.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void showToast(Context context, String s) {
        try{
            Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showLongToast(Context context, String text) {
        try{
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showShortToast(Context context, String text) {
        try{
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

}