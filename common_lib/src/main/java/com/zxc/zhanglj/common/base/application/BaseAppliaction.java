package com.zxc.zhanglj.common.base.application;

import android.app.Application;
import android.content.Context;

import com.zxc.zhanglj.common.config.ConfigUtils;
import com.zxc.zhanglj.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019/1/25 11:22
 */
public class BaseAppliaction extends Application {
    private String TAG ="BaseAppliaction";

    protected static Context mContext;

    protected static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (this) {
            LogUtil.i(TAG, "onCreate");
            ConfigUtils.initMainThread(this);
            mApplication = this;
            mContext = this.getApplicationContext();
        }
    }

    public static Context getInstance() {
        return mContext;
    }

    public static Application getBaseApplication() {
        return mApplication;
    }
}
