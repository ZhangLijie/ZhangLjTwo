package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxc.zhanglj.common.base.baseui.BaseFragment;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.main_lib.fragment.activity.ImageActivity;
import com.zxc.zhanglj.utils.StatusBarUtil;

/**
 * Author:ZLJ
 * Date: 2019/1/24 18:24
 */
public class HomePageFragment extends BaseFragment {
    private static final String TAG = "HomePageFragment";

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
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
        ImageView up_img_iv = mBaseRootView.findViewById(R.id.up_img_iv);
        Button buton_id = mBaseRootView.findViewById(R.id.buton_id);

        textView.setText(TAG);

        up_img_iv.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_girl));
        buton_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.launchActivity(getActivity());
            }
        });
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
