package com.zxc.zhanglj.common.base.baseui;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zxc.zhanglj.common.filter.ClickEventFilter;
import com.zxc.zhanglj.common.stack.ActivityManager;
import com.zxc.zhanglj.commone_lib.R;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.StatusBarUtil;
import com.zxc.zhanglj.utils.WeakHandler;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author:ZLJ
 * Date: 2019/1/24 15:31
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends SupportActivity implements View.OnClickListener {
    private String TAG = "BaseActivity";
    protected T presenter;
    /**
     * 全屏layout
     */
    protected RelativeLayout layoutBaseRoot;
    /**
     * 是否已经被销毁
     */
    public boolean isDestroyed;
    /**
     * 点击事件监听
     */
    private final ClickEventFilter listener = new ClickEventFilter() {

        @Override
        public void onClick(View v) {
            onTvmClick(v);
        }
    };

    private WeakHandler.IHandler iHandler = new WeakHandler.IHandler(){

        @Override
        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case BASE_HANDLER_MSG_ONRESUME:
//
//                    break;
//                case HANDLER_MSG_SHOW_STATUSBAR:
//                    if(mStatusBarView != null && !YaoOSUtil.isOSNotSupportImmersionBar()){
//                        mStatusBarView.setVisibility(View.VISIBLE);
//                    }
//
//                    break;
//                case HANDLER_MSG_HIDE_STATUSBAR:
//
//                    if(mStatusBarView != null && !YaoOSUtil.isOSNotSupportImmersionBar()){
//                        mStatusBarView.setVisibility(View.GONE);
//                    }
//
//                    break;
//            }
        }
    };
    private WeakHandler mBaseHandler= new WeakHandler(iHandler, Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        V view = getPresenterView();

        // 防止子类的initPresenter返回null
        if (presenter != null && view != null) {
            presenter.attach(this, view);
        }

        setContentView(R.layout.base);
        setStatusBar();

        isDestroyed = false;

        initBaseView();

        // 初始化界面所需要数据，如getIntent、savedInstanceState里存放的数据等
        initData(savedInstanceState);

        // 初始化View
        initViews();
        // 加载View需要的数据并刷新View，如发起一个请求，结束后更新View
        loadViewsData();

    }

    @Override
    protected void onDestroy() {
        try {
            LogUtil.d(TAG, "ActivityStackSize BaseActivity onDestroy");

            if (presenter != null) {
                presenter.detach();
            }
            //AppUtils.fixInputMethodManagerLeak(this);
            isDestroyed = true;
            ActivityManager.getScreenManager().popActivity(this);
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	初始化
    private void initBaseView() {
        layoutBaseRoot = (RelativeLayout) findViewById(R.id.layout_base_root);
        LayoutInflater.from(this).inflate(setLayoutId(), layoutBaseRoot);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, 0xff000000);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            LogUtil.d("MainActivityTab", "setOnClickListener7");
            listener.onPreClick(this, v);
        }
    }

    public abstract void onTvmClick(View v);

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 初始化View
     */
    protected void initViews() {
    }

    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 加载view数据
     */
    protected void loadViewsData() {
    }

    ;

    protected abstract T initPresenter();

    protected abstract V getPresenterView();
}
