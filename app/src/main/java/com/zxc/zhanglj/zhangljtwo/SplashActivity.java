package com.zxc.zhanglj.zhangljtwo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zxc.zhanglj.common.base.baseui.BaseActivity;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.common.manager.permission.Permission;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.ToastUtils;
import com.zxc.zhanglj.utils.WeakHandler;

/**
 * Author:ZLJ
 * Date: 2019/3/13 18:24
 */
public class SplashActivity extends BaseActivity implements WeakHandler.IHandler {
    private String TAG = "SplashActivity";
    private static final int REQUEST_PERMISSION_STORAGE = 10010;
    private static final int REQUEST_PERMISSION_PHONE = 10011;
    private WeakHandler mHandler = new WeakHandler(this);

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
        checkPermission();
    }


    private void checkPermission() {
        LogUtil.i(TAG, "111111");
        // 检查权限是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, Permission.STORAGE, REQUEST_PERMISSION_STORAGE);
            }
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, Permission.PHONE, REQUEST_PERMISSION_PHONE);
            }
            LogUtil.i(TAG, "33333");
        } else {
            LogUtil.i(TAG, "44444");
            autoMainActivity(1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (REQUEST_PERMISSION_STORAGE == requestCode) {
            LogUtil.i(TAG, "555");
            ToastUtils.showToast(SplashActivity.this, "q请在设置");
        }
        if (REQUEST_PERMISSION_PHONE == requestCode) {
            LogUtil.i(TAG, "666");
        }
    }

    private void autoMainActivity(long delay) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
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
        }, delay);
    }


    @Override
    public void handleMessage(Message msg) {

    }
}
