package com.zxc.zhanglj.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * Author:ZLJ
 * Date: 2019/1/24 17:32
 */
public class MainTabItemView extends RelativeLayout {

    private View childView;

    public MainTabItemView(Context context) {
        super(context);
    }

    public MainTabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainTabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        childView = getChildAt(0);
    }

    public void doAnimation(Animation anim) {
//        if (childView != null && anim != null) {
//            childView.clearAnimation();
//            childView.startAnimation(anim);
//        }
    }

    public void clearAnim() {
        if (childView != null) {
            childView.clearAnimation();
        }
    }
}
