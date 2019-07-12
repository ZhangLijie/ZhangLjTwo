package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.os.Bundle;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;

import com.zxc.zhanglj.common.base.baseui.BaseFragment;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.common.manager.EventBusManager;
import com.zxc.zhanglj.common.manager.event.StartTabStackEvent;
import com.zxc.zhanglj.common.manager.event.SwitchTabEvent;
import com.zxc.zhanglj.common.manager.event.TabSwitchFriendEvent;
import com.zxc.zhanglj.common.widget.MainTabItemView;
import com.zxc.zhanglj.common.widget.MainTabView;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.ToastUtils;
import com.zxc.zhanglj.utils.WeakHandler;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author:ZLJ
 * Date: 2019/1/24 17:15
 */
public class MainFragment extends BaseFragment implements MainTabView.MainTabClickedListener,WeakHandler.IHandler{
    private String TAG ="MainFragment";
    private SupportFragment[] mFragments = new SupportFragment[5];
    private MainTabView mBottomBar;
    private MainTabItemView mTabYao;
    private View tabLineView;

    public int mTabIndex = MainTabView.TAB_SEND;
    private WeakHandler mHandler = new WeakHandler(this);
    private static final String TAB_SWITCH_INDEX_KEY = "tabSwitchIndex";
    private static final String SAVE_INSTANCE_STATE_TAB_INDEX = "saveInstanceStateTabIndex";
    /**
     * 网络状态异常
     */
    private static final int NET_WORK_ERROR = 20;
    private static final int HANDLER_MSG_SWITCH_TAB_TAB_FIND = 21;
    private static final int HANDLER_MSG_SWITCH_TAB_TAB_SEND = 22;
    private static final int HANDLER_MSG_SWITCH_TAB_TAB_HOME = 23;
    private static final int HANDLER_MSG_SWITCH_TAB_TAB_HOT = 24;
    private static final int HANDLER_MSG_SWITCH_TAB_TAB_ME = 25;
    /**
     * MainActivity onResume
     */
    private final static int MAIN_ONRESUME = 152;

    public static MainFragment newInstance(int tabIndex) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_SWITCH_INDEX_KEY, tabIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseTabIndex();
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        try {
            LogUtil.i(TAG,":initView 0:"+mTabIndex);
            if (savedInstanceState == null) {
                mFragments[MainTabView.TAB_FIND] = FriendsFragment.newInstance();
                mFragments[MainTabView.TAB_HOME] = CostFragment.newInstance();
                mFragments[MainTabView.TAB_SEND] = HomePageFragment.newInstance();
                mFragments[MainTabView.TAB_HOT] = CoinFragment.newInstance();
                mFragments[MainTabView.TAB_ME] = PersonalFragment.newInstance();

                loadMultipleRootFragment(R.id.main_fragment_idss, mTabIndex,
                        mFragments[MainTabView.TAB_FIND],
                        mFragments[MainTabView.TAB_HOME],
                        mFragments[MainTabView.TAB_SEND],
                        mFragments[MainTabView.TAB_HOT],
                        mFragments[MainTabView.TAB_ME]);

                mBottomBar = (MainTabView) mBaseRootView.findViewById(R.id.main_bottom_bar);
                mBottomBar.setTabClickedListener(this);
                tabLineView = mBaseRootView.findViewById(R.id.main_tab_line);

                mTabYao = (MainTabItemView) mBaseRootView.findViewById(R.id.tab_yao);
                mTabYao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.i(TAG,":mTabYao :"+mTabIndex);
                    }
                });
                LogUtil.i(TAG,":initView :"+mTabIndex);
                showTab(MainTabView.TAB_SEND);
            } else {
                // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

                // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
                mFragments[MainTabView.TAB_FIND] = findChildFragment(FriendsFragment.class);
                mFragments[MainTabView.TAB_HOME] = findChildFragment(CostFragment.class);
                mFragments[MainTabView.TAB_SEND] = findChildFragment(HomePageFragment.class);
                mFragments[MainTabView.TAB_HOT] = findChildFragment(CoinFragment.class);
                mFragments[MainTabView.TAB_ME] = findChildFragment(PersonalFragment.class);
                mBottomBar = (MainTabView) mBaseRootView.findViewById(R.id.main_bottom_bar);
                mBottomBar.setTabClickedListener(this);
                tabLineView = mBaseRootView.findViewById(R.id.main_tab_line);

                mTabYao = (MainTabItemView) mBaseRootView.findViewById(R.id.tab_yao);
                mTabYao.setOnClickListener(this);
                showTab(savedInstanceState.getInt(SAVE_INSTANCE_STATE_TAB_INDEX));
            }

//            ((MainActivity) this.getActivity()).addSoftKeyboardStateListener(new MainActivity.SoftKeyboardStateListener() {
//
//                @Override
//                public void onSoftKeyboardOpened(int keyboardHeightInPx) {
//                    setBottomBarShow(false);
//                }
//
//                @Override
//                public void onSoftKeyboardClosed() {
//                    setBottomBarShow(true);
//                }
//            });
            setBottomBarShow(true);
            LogUtil.d(TAG, "initView");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Subscribe
    public void onEvent(TabSwitchFriendEvent event) {
        if (mBottomBar != null) {
            mBottomBar.showFind();
        }
    }
    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void onEvent(StartTabStackEvent event) {
        start(event.targetFragment);
    }

    private void chooseTabIndex() {
        Bundle args = getArguments();
        if (args != null && args.containsKey(TAB_SWITCH_INDEX_KEY)) {
            int tabSwitch = args.getInt(TAB_SWITCH_INDEX_KEY);
            switch (tabSwitch) {
                case MainTabView.TAB_FIND:
                case MainTabView.TAB_HOME:
                case MainTabView.TAB_SEND:
                case MainTabView.TAB_HOT:
                case MainTabView.TAB_ME:
                    mTabIndex = tabSwitch;
                    break;
                default:
                    LogUtil.e(TAG, "onCreate: wrong tabindex extra :" + tabSwitch);
            }
        }
    }

    private void showTab(int type) {
        try {
            if (type == MainTabView.TAB_SEND) {
                LogUtil.d(TAG, "MainShowTab TAB_SEND");
                mBottomBar.showPlay();
            } else {
                LogUtil.d(TAG, "MainShowTab setTab");
                setTab(type);
            }
            mBottomBar.setCheckedTab(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTab(int index) {
        try {
            LogUtil.d(TAG, "setTab index : " + index);
            mTabIndex = index;

            switch (mTabIndex) {
                case MainTabView.TAB_FIND: // 消息

                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(HANDLER_MSG_SWITCH_TAB_TAB_FIND);
                    }

                    break;
                case MainTabView.TAB_HOME: // 好友
                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(HANDLER_MSG_SWITCH_TAB_TAB_SEND);
                    }

                    break;

                case MainTabView.TAB_SEND: // 摇一摇

                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(HANDLER_MSG_SWITCH_TAB_TAB_HOME);
                    }

                    break;

                case MainTabView.TAB_HOT: //发现

                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(HANDLER_MSG_SWITCH_TAB_TAB_HOT);
                    }

                    break;
                case MainTabView.TAB_ME: //发现

                    if (mHandler != null) {
                        mHandler.sendEmptyMessage(HANDLER_MSG_SWITCH_TAB_TAB_ME);
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        Log.i(TAG, "onSupportVisible");
        mHandler.sendEmptyMessage(MAIN_ONRESUME);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_INSTANCE_STATE_TAB_INDEX, mTabIndex);
    }

    @Override
    public void onTvmClick(View v) {
        if (v.getId() == R.id.tab_yao) {
            showTab(MainTabView.TAB_SEND);
        }
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
    public void handleMessage(Message msg) {
        int what = msg.what;
        LogUtil.i(TAG,"msg.what :"+what);
        switch (msg.what) {
            case NET_WORK_ERROR:
                ToastUtils.showToast(_mActivity, "网络似乎有问题");
                break;
            case MAIN_ONRESUME:
//                if (VersionController.getInstance().isAppUpgrade(_mActivity)) {//检查版本更新
//                    return;
//                }
                LogUtil.d(TAG, "AppUpgrade normal");

//                if (!LocalUserModelManager.getInstance().isLogin()) {//判断是否登录
//                    return;
//                }

                updateTabView();//红点消息更新
                //updateChatMsgCountView(null);//聊天消息更新

                break;
            case HANDLER_MSG_SWITCH_TAB_TAB_FIND:
                mTabYao.setSelected(false);
                showHideFragment(mFragments[mTabIndex], null);
                break;
            case HANDLER_MSG_SWITCH_TAB_TAB_SEND:
                mTabYao.setSelected(false);

                showHideFragment(mFragments[mTabIndex], null);
                break;
            case HANDLER_MSG_SWITCH_TAB_TAB_HOME:
                try {
                    if (_mActivity != null) {
                        //检查进程存活
                        //ConfigUtil.checkServiceAvailable(_mActivity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mTabYao.setSelected(true);

                showHideFragment(mFragments[mTabIndex], null);

//                showMainYaoTabInfo();首页引导图
                break;
            case HANDLER_MSG_SWITCH_TAB_TAB_HOT:
                mTabYao.setSelected(false);

                showHideFragment(mFragments[mTabIndex], null);
                break;
            case HANDLER_MSG_SWITCH_TAB_TAB_ME:
                mTabYao.setSelected(false);

                showHideFragment(mFragments[mTabIndex], null);

//                showBindWxToast();
                break;
        }
    }

    @Override
    public void onTabItemClicked(int lastType, int type) {
        try {
            LogUtil.i(TAG, "lastType=" + lastType + "," + "type=" + type);

            if (lastType == type) {
                return;
            }

            switch (type) {
                case MainTabView.TAB_FIND:
                    setTab(MainTabView.TAB_FIND);
                    break;
                case MainTabView.TAB_HOME:
                    jumpToTab(MainTabView.TAB_HOME);
                    break;

                case MainTabView.TAB_SEND:
                    setTab(MainTabView.TAB_SEND);
                    break;
                case MainTabView.TAB_HOT:
                    jumpToTab(MainTabView.TAB_HOT);
                    break;
                case MainTabView.TAB_ME:
                    jumpToTab(MainTabView.TAB_ME);
                    break;
            }

            EventBusManager.getInstance().post(new SwitchTabEvent(lastType, type));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTabView() {//消息红点

//        if (LocalUserModelManager.getInstance().getCachedUserModel() == null || LocalUserModelManager.getInstance().getCachedUserModel().getToken() == null) {
//            return;
//        }
//
//        LogUtil.d(TAG, "updateTabView");
//
//        // 显示朋友消息
//        YaoTaskManager.getInstance().addTask(new YaoTaskExecutor<MainTablePushModel>() {
//            @Override
//            public MainTablePushModel exec() throws Exception {
//                return PushMessageModelManager.queryCategoryDataByCategory(_mActivity,
//                        RedDotPushModel.CategoryType.CATEGORY_TYPE_MESSAGE.category);
//            }
//
//            @Override
//            public void onMainSuccess(MainTablePushModel result) {
//                doUpdateTabMsgView(result);
//            }
//        }.setTag(YaoTaskExecutor.TAG_QUERY_CATEGORY_MESSAGE), ThreadPoolManager.getInstance().getSingleExecutor());
//
//        // 显示"我"的消息
//        YaoTaskManager.getInstance().addTask(new YaoTaskExecutor<MainTablePushModel>() {
//            @Override
//            public MainTablePushModel exec() throws Exception {
//                return PushMessageModelManager.queryCategoryDataByCategory(_mActivity,
//                        RedDotPushModel.CategoryType.CATEGORY_TYPE_MINE.category);
//            }
//
//            @Override
//            public void onMainSuccess(MainTablePushModel result) {
//                doUpdateTabMsgView(result);
//            }
//        }.setTag(YaoTaskExecutor.TAG_QUERY_CATEGORY_PERSONAL), ThreadPoolManager.getInstance().getSingleExecutor());
//
    }

    public void setBottomBarShow(boolean isShow) {
        if (mBottomBar != null && tabLineView != null) {
            if (isShow) {
                mBottomBar.setVisibility(View.VISIBLE);
                tabLineView.setVisibility(View.VISIBLE);
            } else {
                mBottomBar.setVisibility(View.GONE);
                tabLineView.setVisibility(View.GONE);
            }
        }
    }


    public void jumpToTab(int type) {
        mBottomBar.setCheckedTab(type);
        setTab(type);
    }
}
