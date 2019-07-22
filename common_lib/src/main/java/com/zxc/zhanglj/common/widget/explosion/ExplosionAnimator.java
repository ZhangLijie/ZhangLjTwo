package com.zxc.zhanglj.common.widget.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Author:ZLJ
 * Date: 2019-07-20 11:03
 */
public class ExplosionAnimator extends ValueAnimator {
    //获取具体当前动画执行到哪一步
    public int DEFAULT_DURATION =1500;
    private Particle[][] mParticles;
    private Paint mPaint;
    private View mContainer;
    private ParticleFactory mParticleFactory;

    public ExplosionAnimator(ExplosionField view, Bitmap bitmap, Rect rect,ParticleFactory particleFactory){
        this.mParticleFactory =particleFactory;
        this.mPaint = new Paint();
        this.mContainer=view;
        this.setFloatValues(new float[]{0.0f,1.0f});
        this.setDuration((long)this.DEFAULT_DURATION);
        this.mParticles = this.mParticleFactory.generateParticles(bitmap,rect);
        bitmap.recycle();
    }

    public void draw(Canvas canvas){
        if(!isStarted()){//动画结束
            return;
        }
        //所有的粒子开始运动
        for(Particle[] mPrticle :mParticles){
            for(Particle particle :mPrticle){
                //单个粒子
                particle.advance(canvas,mPaint,(Float) getAnimatedValue());
            }
            mContainer.invalidate();
        }
    }
    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
