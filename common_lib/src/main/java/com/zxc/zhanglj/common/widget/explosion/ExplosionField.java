package com.zxc.zhanglj.common.widget.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Author:ZLJ
 * Date: 2019-07-20 10:45
 */
public class ExplosionField extends View {
    private ArrayList<ExplosionAnimator> explosionAnimators;//存储动画

    private HashMap<View, ExplosionAnimator> explosionAnimatorMap;
    private OnClickListener onClickListener;
    private ParticleFactory particleFactory;
    private ClickCallback clickCallback;


    //出入粒子工厂
    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }


    private void init(ParticleFactory particleFactory) {
        //数据初始化
        explosionAnimators = new ArrayList<>();
        this.particleFactory = particleFactory;
        //将Fileld添加到屏幕上

        attach2Activity((Activity) getContext());

    }

    private void attach2Activity(Activity context) {
        //
        ViewGroup rootview = context.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootview.addView(this, lp);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //反复执行
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }

    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();

            for (int i = 0; i < childCount; ++i) {
                this.addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(this.getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if (null == this.onClickListener) {
            this.onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //粒子特效
                    ExplosionField.this.explode(v);
                    if (ExplosionField.this.clickCallback != null) {
                        ExplosionField.this.clickCallback.onClick(v);
                    }
                }

            };
        }
        return this.onClickListener;
    }

    //粒子动画入口
    private void explode(final View view) {
        //确定粒子的位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//获取view相对屏幕的位置
        //actionBar的高度
        int contentTop = ((ViewGroup) getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        //位置对齐
        rect.offset(0, -contentTop - statusBarHeight);
        //震动
        ValueAnimator animator = new ValueAnimator().ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //恢复位置
                view.setTranslationX(0);
                view.setTranslationY(0);
                explode2(view, rect);
            }
        });
        animator.start();
    }

    private void explode2(final View view, Rect rect) {
        final ExplosionAnimator animator = new ExplosionAnimator(this, Utils.creatBitmapFromView(view), rect, particleFactory);
        explosionAnimators.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setClickable(false);
                view.animate().setDuration(150L).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().alpha(1.0F).scaleX(1.0F).scaleY(1.0F).setDuration(150L).start();
                view.setClickable(true);
                explosionAnimators.remove(animator);
//                explosionAnimatorMap.remove(view);
            }
        });
        animator.start();
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}
