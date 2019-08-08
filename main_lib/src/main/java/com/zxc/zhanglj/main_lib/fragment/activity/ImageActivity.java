package com.zxc.zhanglj.main_lib.fragment.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.zxc.zhanglj.common.base.baseui.BaseActivity;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.utils.StatusBarUtil;

/**
 * Author:ZLJ
 * Date: 2019-08-08 14:36
 */
public class ImageActivity extends BaseActivity {

    private LinearLayout img_ll;
    public static void launchActivity(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity,ImageActivity.class);
        activity.startActivity(intent);

    }
    @Override
    public void onTvmClick(View v) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_img;
    }

    @Override
    protected void initViews() {
      img_ll =   findViewById(R.id.img_ll);
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
    protected void setStatusBar() {

//        StatusBarUtil.setTranslucentForImageView(this, img_ll);
        StatusBarUtil.setTransparentForImageView(this, img_ll);
    }
}
