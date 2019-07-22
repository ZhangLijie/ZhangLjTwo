package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Author:ZLJ
 * Date: 2019-07-20 12:41
 */
public class FallingParticle extends Particle {

    public static final int PART_WH = 8;
    float radius = 8.0f;
    float alpha = 1.0f;
    Rect mBound;

    public FallingParticle(int color,float x,float y,Rect bound){
        super(color,x,y);
        this.mBound = bound;
    }
    @Override
    protected void draw(Canvas canvas, Paint paint) {

    }

    @Override
    protected void calculate(float factor) {
        this.cx += factor*(float) Utils.RANDOM.nextInt(this.mBound.width())*(Utils.RANDOM.nextFloat()-0.5F);
        this.cy += factor*(float)Utils.RANDOM.nextInt(this.mBound.height()/2);
        this.radius -=factor*(float)Utils.RANDOM.nextInt(2);
        this.alpha = (1.0F-factor)*(1.0F+Utils.RANDOM.nextFloat());
    }
}
