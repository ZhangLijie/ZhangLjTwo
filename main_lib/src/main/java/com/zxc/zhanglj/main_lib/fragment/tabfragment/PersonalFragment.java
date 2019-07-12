package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxc.zhanglj.common.base.baseui.BaseFragment;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.main_lib.R;


/**
 * Author:ZLJ
 * Date: 2019/1/24 18:24
 */
public class PersonalFragment extends BaseFragment {
    private static final String TAG = "PersonalFragment";

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();

        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_common;
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
