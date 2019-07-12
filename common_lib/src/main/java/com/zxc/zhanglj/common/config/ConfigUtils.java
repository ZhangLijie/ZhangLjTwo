package com.zxc.zhanglj.common.config;

import android.app.Application;
import android.content.Context;


import com.zxc.zhanglj.common.base.application.BaseAppliaction;
import com.zxc.zhanglj.common.core.crash.reportlog.ReportManager;
import com.zxc.zhanglj.common.core.crash.reportlog.collector.ReportData;
import com.zxc.zhanglj.common.core.crash.reportlog.sender.ReportSender;
import com.zxc.zhanglj.utils.AppUtils;
import com.zxc.zhanglj.utils.GlobalInit;
import com.zxc.zhanglj.utils.LogUtil;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Author:ZLJ
 * Date: 2019/1/25 11:23
 */
public class ConfigUtils {
    private static final String TAG = "ConfigUtil";

    private static boolean isInitCrashReportManager = false;


    public static void initMainThread(Application application) {

        initCommonConfig(application);
        initFragmentManager();
    }

    private static void initFragmentManager() {
        LogUtil.i("ConfigUtils","initFragmentManager");
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.NONE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
//                .debug(BuildConfig.DEBUG)
                .debug(false)
                // 在debug=false时，即线上环境时，上述异常会被捕获并回调ExceptionHandler
                .handleException(new ExceptionHandler() {

                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    /**
     * 初始化App配置
     */
    public static void initCommonConfig(Context context) {

        // 获取版本号
        GlobalInit.VERSION_NAME = AppUtils.getVersionName(context);
        GlobalInit.PACKAGE_NAME = AppUtils.getPackageName(context);
        //获取配置的debug和dev值
        boolean appDev = (boolean) AppUtils.getMetaData(context, AppConstants.APP_ENV_DEV);
        boolean appDebug = (boolean) AppUtils.getMetaData(context, AppConstants.APP_ENV_DEBUG);

        //App的环境变量
        AppConstants.setAppDev(appDev);
        AppConstants.setAppDebug(appDebug);
        LogUtil.isDebug = AppConstants.isAppDebug();
        LogUtil.d(TAG, "appDev : " + appDev);

        LogUtil.d(TAG, "appDebug : " + appDebug);

        //BaseModule的环境变量
        GlobalInit.APP_DEBUG = AppConstants.isAppDebug();

    }



    /**
     * 本地日志收集
     */
    public static void initCrashReportManager(Context context) {
        if (isInitCrashReportManager) {
            return;
        }

        isInitCrashReportManager = true;

        ReportManager.getInstance().init(context, new ReportSender() {

            @Override
            public void send(ReportData errorContent) {
            }
        });
    }
}
