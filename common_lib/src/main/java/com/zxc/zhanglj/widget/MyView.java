package com.zxc.zhanglj.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zxc.zhanglj.common.base.application.BaseAppliaction;
import com.zxc.zhanglj.commone_lib.R;
import com.zxc.zhanglj.utils.ScreenUtil;

import java.util.ArrayList;

/**
 * Author:ZLJ
 * Date: 2019/3/17 12:27
 */
public class MyView extends View {

    private static int DEFAULT_COLOR = R.color.colorAccent;


    public static int TOTAL_DURATION = 10000;
    public static int DEFAULT_PROGRESS_COLOR = Color.BLUE;
    public static int DEFAULT_BACKGROUND_COLOR = Color.GRAY;
    public static int DEFAULT_STROKE_WIDTH = ScreenUtil.dip2px(BaseAppliaction.getInstance(),40);
    public static int DEFAULT_TEXT_SIZE = ScreenUtil.sp2px(BaseAppliaction.getInstance(),28);
    public static int DEFAULT_TEXT_COLOR = Color.BLACK;

    private Paint mBackgroundPaint = new Paint();
    private Paint mPaint = new Paint();
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mProgressColor;
    private int mBackgroundColor;
    private int mStrokeWidth;
    private int mMaxValue = 100;
    private int mCurrentValue = 0;


    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        typedArray.getColor(R.styleable.MyView_progress_color, DEFAULT_PROGRESS_COLOR);
        typedArray.recycle();

    }

    private int initState(){
        int jj = 0;
        ArrayList<String> strings = new ArrayList<>();
        try {
            for (String hh : strings) {
                strings.remove(hh);
            }
            return 0;
        }catch (Exception e){

        }finally {
            return 1;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


}
