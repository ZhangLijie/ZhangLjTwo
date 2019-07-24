package com.zxc.zhanglj.main_lib.fragment.tabfragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zxc.zhanglj.common.base.baseui.BaseActivity;
import com.zxc.zhanglj.common.base.baseui.BasePresenter;
import com.zxc.zhanglj.main_lib.R;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.ScreenUtil;

/**
 * Author:ZLJ
 * Date: 2019-07-24 15:59
 */
public class SlidingSimpleActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private String TAG = "SlidingSimpleActivity";

    private float mAppLayoutAlpha;
    int oldoffset = 0;
    private AppBarLayout mAppBar;
    private ImageView mIvIntro;//头部引导图
    private RelativeLayout mRlSearch;//搜索框
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public void onTvmClick(View v) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_sliding_simple_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();

        mAppBar = findViewById(R.id.app_bar_layout);

        mIvIntro = findViewById(R.id.id_img_tb);
        mRlSearch = findViewById(R.id.id_rl_home_search);
        mCollapsingToolbarLayout = findViewById(R.id.id_collapsing_toolbar);
        mCollapsingToolbarLayout.setMinimumHeight(ScreenUtil.dip2px(getBaseContext(), 20));

        mAppBar.addOnOffsetChangedListener(this);

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
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        LogUtil.i(TAG, "verticalOffset= " + verticalOffset + ",oldOffset=" + oldoffset);
        mAppLayoutAlpha = verticalOffset * 1.0F / appBarLayout.getTotalScrollRange();
        //修改搜索布局透明度
        mRlSearch.setAlpha(1 - Math.abs(mAppLayoutAlpha));
        mCollapsingToolbarLayout.setAlpha(1 - Math.abs(mAppLayoutAlpha));

        if (mIvIntro.getVisibility() == View.VISIBLE) {
            //修改新人引导透明度
            mIvIntro.setAlpha(1 - Math.abs(mAppLayoutAlpha));
        }

    }
}
