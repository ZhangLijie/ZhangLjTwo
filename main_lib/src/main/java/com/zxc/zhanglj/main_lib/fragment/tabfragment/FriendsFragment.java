package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxc.zhanglj.common.base.baseui.BaseFragment;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.common.widget.explosion.BooleanFactory;
import com.zxc.zhanglj.common.widget.explosion.ClickCallback;
import com.zxc.zhanglj.common.widget.explosion.ExplosionField;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.ToastUtils;

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
        return R.layout.fragment_common;
    }

    ImageView img_ll;
    TextView textView;

    @Override
    protected void initView(Bundle savedInstanceState) {
         img_ll= mBaseRootView.findViewById(R.id.img_ll);
         textView = mBaseRootView.findViewById(R.id.text_view);
        textView.setText(TAG);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(getContext(),"vv点击");
            }
        });
        initExplosion();
    }

    @Override
    public void onTvmClick(View v) {

    }


    private void initExplosion(){
        ExplosionField explosionField = new ExplosionField(this.getActivity(), new BooleanFactory());
        explosionField.setClickCallback(new ClickCallback() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast(getContext(),"initExplosion v点击");
            }
        });
        explosionField.addListener(img_ll);
        explosionField.addListener(textView);

    }


    private void  initFirstData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG,"initFirstData");
                inittwoThread();
            }
        }).start();
    }


    private void inittwoThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG,"inittwoThread");
            }
        }).start();
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
