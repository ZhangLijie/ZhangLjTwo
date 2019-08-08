package com.zxc.zhanglj.common.widget.explosion;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:ZLJ
 * Date: 2019-07-26 14:07
 */
public class TextViewGroup extends ViewGroup {
    public TextViewGroup(Context context) {
        super(context);
    }

    public TextViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++ ){
            View childAt = getChildAt(i);

        }
    }

}
