package com.zxc.zhanglj.zhangljfirst.commons.filter;

import android.app.Activity;
import android.view.View;

import com.zxc.zhanglj.zhangljfirst.R;
import com.zxc.zhanglj.zhangljfirst.commons.stack.ActivityManager;
import com.zxc.zhanglj.zhangljfirst.utils.ClickUtil;
import com.zxc.zhanglj.zhangljfirst.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019/1/24 16:16
 */
public abstract class ClickEventFilter {

    /**
     * 默认所有的按钮都需要跳转
     */
    private boolean needCheck = true;

    /**
     * 连续点击是否有间隔时间
     */
    private boolean needPeriod = true;

    public void setNeedCheck(boolean needCheck) {
        this.needCheck = needCheck;
    }

    public void setNeedPeriod(boolean needPeriod) {
        this.needPeriod = needPeriod;
    }

    public boolean isNeedPeriod() {
        return needPeriod;
    }

    public final void onPreClick(Activity activity, View v) {
        if (needCheck) {
            if (!isNormal(activity, v)) {
                LogUtil.d("MainActivityTab", "setOnClickListener9999999");
                //处理跳转登录
//                if (!LocalUserModelManager.getInstance().isLogin()) {
//                    UserLoginActivity.launchActivity(activity, AppConstants.LOGIN_FROM_MAINACTIVITY);
//                }
                return;
            }
        }

        if (needPeriod) {
            LogUtil.d("MainActivityTab", "setOnClickListener4");
            if (!ClickUtil.isEffectiveClick()) {
                onClick(v);
            }
        } else {
            onClick(v);
        }
    }

    private boolean isNormal(Activity activity, View v) {
//        if (LocalUserModelManager.getInstance().getCachedUserModel() != null) {
        if(true){//处理用户信息
            return true;
        }
        boolean isEffect = false;
        switch (v.getId()) {
            case R.id.tab_yao:
            case R.id.tab_play_tv:
                isEffect = true;
                break;
            default:
                isEffect = false;
        }
        return isEffect;
    }

    public abstract void onClick(View v);

    /**
     * 自定义点击事件
     */
    public static abstract class OnTvmOnClickListener implements View.OnClickListener {

        private ClickEventFilter clickAction = new ClickEventFilter() {

            @Override
            public void onClick(View v) {
                LogUtil.d("MainActivityTab", "setOnClickListener5");
                onTvmClick(v);
            }
        };

        public void setCheck(boolean isCheck) {
            clickAction.setNeedCheck(isCheck);
        }

        public OnTvmOnClickListener setNeedPeriod(boolean needPeriod) {
            clickAction.setNeedPeriod(needPeriod);
            return this;
        }

        @Override
        public void onClick(View v) {
            LogUtil.d("MainActivityTab", "setOnClickListener3");
            clickAction.onPreClick(ActivityManager.getScreenManager().getTopActivity(), v);
        }

        public abstract void onTvmClick(View v);
    }

}
