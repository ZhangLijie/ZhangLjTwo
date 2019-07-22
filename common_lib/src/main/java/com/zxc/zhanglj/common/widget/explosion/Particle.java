package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author:ZLJ
 * Date: 2019-07-20 10:47
 */
 public abstract class Particle {
    protected float cx;
    protected float cy;
    protected int color;
    public Particle(int color,float x,float y){
        this.color = color;
        this.cx=x;
        this.cy = y;
    }
    //
    public void advance(Canvas canvas, Paint paint,float factor){
        //计算我当前的位置
        calculate(factor);
        draw(canvas,paint);
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void calculate(float factor);


}
