package com.zxc.zhanglj.common.config;

/**
 * Author:ZLJ
 * Date: 2019/1/24 15:42
 */
public class AppConstants  {
    /*--------------------------------  App配置常量  --------------------------------*/

    public static boolean isVirtualAPK = false;

    /**
     * 是否是debug版本
     */
    private static boolean APP_DEBUG = false;

    /**
     * 是否是dev环境
     */
    private static boolean APP_DEV = false;

    public static boolean isAppDebug() {
        return APP_DEBUG;
    }

    public static void setAppDebug(boolean appDebug) {
        APP_DEBUG = appDebug;
    }

    public static boolean isAppDev() {
        return APP_DEV;
    }

    public static void setAppDev(boolean appDev) {
        APP_DEV = appDev;
    }
}
