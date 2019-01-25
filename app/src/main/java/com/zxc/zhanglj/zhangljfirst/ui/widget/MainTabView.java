package com.zxc.zhanglj.zhangljfirst.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxc.zhanglj.zhangljfirst.R;
import com.zxc.zhanglj.zhangljfirst.commons.filter.ClickEventFilter;
import com.zxc.zhanglj.zhangljfirst.utils.LogUtil;

/**
 * Author:ZLJ
 * Date: 2019/1/24 17:28
 */
public class MainTabView extends LinearLayout {
private String TAG ="MainTabView";
    public static final int TAB_NONE = -1;
    public static final int TAB_FIND = 0;
    public static final int TAB_HOME = 1;
    public static final int TAB_SEND = 2;
    public static final int TAB_HOT = 3;
    public static final int TAB_ME = 4;

    private MainTabItemView mFindTab;
    private MainTabItemView mHomeTab;
    private MainTabItemView mPlayTab;
    private MainTabItemView mHotTab;
    private MainTabItemView mUserTab;

    private int mType;
    private MainTabItemView mCheckedView;

//    private View mFindNewIndicator;

    private View mMsgIndicator;
    private View mContactIndicator;
    private View mHotIndicator;
    private View mUserIndicator;

    private TextView mUnreadMsgView;
    private TextView mUnreadContactView;
    private TextView mUnreadHotView;
    private TextView mUnreadUserView;

    private MainTabClickedListener mTabClickedListener;

    private Animation mScaleAnim;

    public MainTabView(Context context) {
        super(context);
        init();
    }
    public MainTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        removeAllViews();
        setOrientation(LinearLayout.HORIZONTAL);
        View rootView;
        rootView = inflate(getContext(), R.layout.main_bottom_tab, this);
        rootView.setBackgroundColor(getContext().getResources().getColor(R.color.main_tab_bg_color_press));
        mHomeTab = (MainTabItemView) rootView.findViewById(R.id.bottom_tab_home);
        mFindTab = (MainTabItemView) rootView.findViewById(R.id.bottom_tab_find);
        mPlayTab = (MainTabItemView) rootView.findViewById(R.id.bottom_tab_play);
        mHotTab = (MainTabItemView) rootView.findViewById(R.id.bottom_tab_hot);
        mUserTab = (MainTabItemView) rootView.findViewById(R.id.bottom_tab_user);

        mMsgIndicator = rootView.findViewById(R.id.bottom_tab_msg_indicator);
        mUnreadMsgView = (TextView) rootView.findViewById(R.id.bottom_tab_msg_unread_num);

        mContactIndicator = rootView.findViewById(R.id.bottom_tab_contact_indicator);
        mUnreadContactView = (TextView) rootView.findViewById(R.id.bottom_tab_contact_unread_num);

        mHotIndicator = rootView.findViewById(R.id.bottom_tab_hot_indicator);
        mUnreadHotView = (TextView) rootView.findViewById(R.id.bottom_tab_hot_unread_num);

        mUserIndicator = rootView.findViewById(R.id.bottom_tab_user_indicator);
        mUnreadUserView = (TextView) rootView.findViewById(R.id.bottom_tab_user_unread_num);

        mHomeTab.setOnClickListener(new ClickEventFilter.OnTvmOnClickListener() {
            @Override
            public void onTvmClick(View v) {
                showHome();
            }
        });
        mFindTab.setOnClickListener(new ClickEventFilter.OnTvmOnClickListener() {
            @Override
            public void onTvmClick(View v) {
                showFind();
            }
        });
        mPlayTab.setOnClickListener(new ClickEventFilter.OnTvmOnClickListener() {
            @Override
            public void onTvmClick(View v) {
                showPlay();
            }
        });
        mHotTab.setOnClickListener(new ClickEventFilter.OnTvmOnClickListener() {
            @Override
            public void onTvmClick(View v) {
                showHot();
            }
        });
        mUserTab.setOnClickListener(new ClickEventFilter.OnTvmOnClickListener() {
            @Override
            public void onTvmClick(View v) {
                showUser();
            }
        });

        mScaleAnim = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnim.setInterpolator(new CycleInterpolator(1f));
        mScaleAnim.setDuration(260);
    }

    public void showHome() {
        checkedTab(mHomeTab);
        onClickedTab(TAB_HOME);
    }

    public void showFind() {
        checkedTab(mFindTab);
        onClickedTab(TAB_FIND);
    }

    public void showPlay() {
        checkedTab(mPlayTab);
        onClickedTab(TAB_SEND);
    }

    public void showHot() {
        checkedTab(mHotTab);
        onClickedTab(TAB_HOT);
    }

    public void showUser() {
        LogUtil.i(TAG,"showUser");
        checkedTab(mUserTab);
        onClickedTab(TAB_ME);
    }

    private void checkedTab(MainTabItemView view) {
        if (mCheckedView == view) return;

        if (mCheckedView != null) {
//            mCheckedView.clearAnim();
            mCheckedView.setSelected(false);
        }
        mCheckedView = view;
        mCheckedView.setSelected(true);
//        mCheckedView.doAnimation(mScaleAnim);
    }

    private void onClickedTab(int type) {
        if (mType == type && mType != TAB_ME && mType != TAB_SEND) {
            return;
        }

        if (mTabClickedListener != null) {
            mTabClickedListener.onTabItemClicked(mType, type);
        }
        mType = type;

    }

    public Animation getTabAnimation() {
        return mScaleAnim;
    }

    public void setTabClickedListener(MainTabClickedListener tabClickedListener) {
        this.mTabClickedListener = tabClickedListener;
    }

    public void setCheckedTab(int type) {
        mType = type;
        if (type == TAB_HOME) {
            checkedTab(mHomeTab);
        } else if (type == TAB_FIND) {
            checkedTab(mFindTab);
        } else if (type == TAB_SEND) {
            checkedTab(mPlayTab);
        } else if (type == TAB_HOT) {
            checkedTab(mHotTab);
        } else if (type == TAB_ME) {
            checkedTab(mUserTab);
        }
    }


    public interface MainTabClickedListener {
        void onTabItemClicked(int lastType, int type);
    }
}
