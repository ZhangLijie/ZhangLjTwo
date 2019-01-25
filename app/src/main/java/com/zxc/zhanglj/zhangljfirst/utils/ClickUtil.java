package com.zxc.zhanglj.zhangljfirst.utils;

/**
 * 防连续点击
 */
public class ClickUtil {

    private static final String TAG = "ClickEffectUtil";
    /**
     * 上一次点击时间
     */
    private static long lastClickTime;

    /**
     * 是否是有效点击
     * @return
     */
    public static boolean isEffectiveClick() { 
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;

        LogUtil.d(TAG, "time period is "+timeD);

        if ( 0 < timeD && timeD < 200) {
            return true;    
        }

        lastClickTime = time;

        return false;    
    } 
} 

