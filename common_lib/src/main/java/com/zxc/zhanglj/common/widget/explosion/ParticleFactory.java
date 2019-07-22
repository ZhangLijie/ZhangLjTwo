package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Author:ZLJ
 * Date: 2019-07-20 10:58
 */
public abstract class ParticleFactory {
    public ParticleFactory(){}
    //生成粒子
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect rect);
}
