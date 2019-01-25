package com.zxc.zhanglj.zhangljfirst.commons.appliaction;

import android.app.Application;

import com.zxc.zhanglj.zhangljfirst.commons.config.ConfigUtils;
import com.zxc.zhanglj.zhangljfirst.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019/1/25 11:22
 */
public class BaseAppliaction extends Application {
    private String TAG ="BaseAppliaction";
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG,"onCreate");
        ConfigUtils.initMainThread(this);
    }
}
