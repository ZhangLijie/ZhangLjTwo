package com.zxc.zhanglj.zhangljfirst.ui.tabfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxc.zhanglj.zhangljfirst.R;
import com.zxc.zhanglj.zhangljfirst.commons.Base.BaseFragment;
import com.zxc.zhanglj.zhangljfirst.commons.Base.BasePresenter;

/**
 * Author:ZLJ
 * Date: 2019/1/24 18:24
 */
public class FriendsFragment extends BaseFragment {
    private static final String TAG = "FriendsFragment";

    public static FriendsFragment newInstance() {
        Bundle args = new Bundle();

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        TextView textView = mBaseRootView.findViewById(R.id.text_view);
        textView.setText(TAG);

    }

    @Override
    public void onTvmClick(View v) {

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
