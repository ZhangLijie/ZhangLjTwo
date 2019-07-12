package com.zxc.zhanglj.zhangljtwo;

import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zxc.zhanglj.common.base.baseui.BaseActivity;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019/3/13 18:24
 */
public class SplashActivity extends BaseActivity {
    private String TAG = "SplashActivity";

    @Override
    public void onTvmClick(View v) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected Object getPresenterView() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ARouter.getInstance().build("/zhangmain/MainActivity").navigation(SplashActivity.this, new NavigationCallback() {

            @Override
            public void onFound(Postcard postcard) {
                LogUtil.i(TAG, "onFound");
            }

            @Override
            public void onLost(Postcard postcard) {
                LogUtil.i(TAG, "onLost");
            }

            @Override
            public void onArrival(Postcard postcard) {
                LogUtil.i(TAG, "onArrival");
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                LogUtil.i(TAG, "onInterrupt");
            }
        });
    }
}
