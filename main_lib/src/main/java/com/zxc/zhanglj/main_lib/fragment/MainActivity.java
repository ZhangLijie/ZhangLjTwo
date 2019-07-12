package com.zxc.zhanglj.main_lib.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zxc.zhanglj.common.base.baseui.BaseActivity;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.common.manager.EventBusManager;
import com.zxc.zhanglj.common.manager.event.SwitchTabEvent;
import com.zxc.zhanglj.common.manager.event.TabSwitchFriendEvent;
import com.zxc.zhanglj.common.widget.MainTabView;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.main_lib.fragment.tabfragment.FriendsFragment;
import com.zxc.zhanglj.main_lib.fragment.tabfragment.MainFragment;
import com.zxc.zhanglj.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
@Route(path = "/zhangmain/MainActivity")
public class MainActivity extends BaseActivity {

    private String TAG = "MainActivity";
    private int mTabIndex = MainTabView.TAB_SEND;

    private FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);

            LogUtil.i(TAG, "onFragmentResumed--->" + f.getClass().getSimpleName());

            if (FriendsFragment.class.getName().equals(f.getClass().getName())) {
                if (mTabIndex != MainTabView.TAB_NONE) {
                    resetTabIndex();
                    EventBusManager.getInstance().getEventBus().post(new TabSwitchFriendEvent());
                }
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance(mTabIndex));

        }

//        connectSocket();

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);

    }

    @Override
    public void onTvmClick(View v) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        DefaultHorizontalAnimator animator = new DefaultHorizontalAnimator();
        animator.setPopEnter(0);
        animator.setPopExit(0);
        return animator;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(fragmentLifecycleCallbacks != null){
            getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
        }
    }

    @Override
    protected void initViews() {
        resetTabIndex();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe
    public void onEvent(SwitchTabEvent event) {
        LogUtil.d(TAG, "onEvent--SwitchTabEvent:" + event.targetType);
        if (event.targetType == MainTabView.TAB_FIND) {
            resetTabIndex();
        }
    }
    private void resetTabIndex() {
        mTabIndex = MainTabView.TAB_NONE;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return goHome(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean goHome(int keyCode, KeyEvent event) {
        LogUtil.d(TAG, "BaseActivity 按下HOME键");
        //EventBusManager.getInstance().post(new AppActionEvent(AppActionEvent.AppAction.BACK));

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected Object getPresenterView() {
        return null;
    }
}
