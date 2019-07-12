package com.zxc.zhanglj.zhangljtwo.application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zxc.zhanglj.common.base.application.BaseAppliaction;
import com.zxc.zhanglj.common.config.ConfigUtils;

/**
 * Author:ZLJ
 * Date: 2019/3/13 17:55
 */
public class ZljApplication extends BaseAppliaction {
    @Override
    public void onCreate() {
        super.onCreate();

        if (true) {//AppUtils.isAppDebug()
            // 开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        ConfigUtils.initMainThread(this);
    }
}
