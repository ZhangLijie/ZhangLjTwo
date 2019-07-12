package com.zxc.zhanglj.common.base.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zxc.zhanglj.common.filter.ClickEventFilter;
import com.zxc.zhanglj.commone_lib.R;
import com.zxc.zhanglj.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author:ZLJ
 * Date: 2019/1/24 15:31
 */
public abstract class BaseFragment<V,T extends BasePresenter<V>> extends SupportFragment implements View.OnClickListener {
private String TAG ="BaseFragment";
    protected FrameLayout mBaseRootView;

    protected T presenter;
    /**
     * 点击事件监听
     */
    private final ClickEventFilter listener = new ClickEventFilter() {

        @Override
        public void onClick(View v) {
            onTvmClick(v);
        }
    };

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        mBaseRootView = (FrameLayout) inflater.inflate(R.layout.fragment_base, container, false);


        mBaseRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do nothing
            }
        });


        presenter = initPresenter();
        V view = getPresenterView();

        // 防止子类的initPresenter返回null
        if(presenter != null && view != null){
            presenter.attach(getContext(),view);
        }

        return mBaseRootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addChildView();
        initView(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            LogUtil.d("MainActivityTab","setOnClickListener8");
            listener.onPreClick(getActivity(),v);
        }
    }

    //	初始化
    private void addChildView() {
        try{
            LogUtil.i(TAG,"addChildView :");
            if(setLayoutId() > 0){
                LogUtil.i(TAG,"addChildView 99999:");
                LayoutInflater.from(_mActivity).inflate(setLayoutId(), mBaseRootView);


            }else if(setChildView() != null){
                LogUtil.i(TAG,"addChildView 66666:");
                mBaseRootView.addView(setChildView(),new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT));
            }

        }catch (Exception e){
            e.printStackTrace();
            LogUtil.i(TAG,"addChildView e:"+e.toString());
        }
    }

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int setLayoutId();

    protected View setChildView(){
        return null;
    }


    protected abstract void initView(Bundle savedInstanceState);

    public abstract void onTvmClick(View v);

    protected abstract T initPresenter();

    protected abstract V getPresenterView();
}
