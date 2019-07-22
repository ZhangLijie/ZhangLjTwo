package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static com.zxc.zhanglj.common.widget.explosion.FallingParticle.PART_WH;
import static com.zxc.zhanglj.common.widget.explosion.Utils.RANDOM;

/**
 * Author:ZLJ
 * Date: 2019-07-20 13:46
 */
public class BooleanParticle extends Particle {
    float radius=PART_WH;
    float alpha;
    Rect mBound;
    public BooleanParticle(int color,float x,float y,Rect bound){
        super(color,x,y);
        mBound = bound;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius, paint);

    }

    @Override
    protected void calculate(float factor) {
        cx = cx + factor * RANDOM.nextInt(mBound.width()) * (RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * RANDOM.nextInt(mBound.height()) * (RANDOM.nextFloat() - 0.5f);

        radius = radius-factor*RANDOM.nextInt(2);
        alpha=(1f-factor)*(1+RANDOM.nextFloat());
    }
}
