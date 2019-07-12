package com.zxc.zhanglj.common.config;

import android.app.Application;


import com.zxc.zhanglj.utils.LogUtil;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Author:ZLJ
 * Date: 2019/1/25 11:23
 */
public class ConfigUtils {
    public static void initMainThread(final Application application) {
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
}
