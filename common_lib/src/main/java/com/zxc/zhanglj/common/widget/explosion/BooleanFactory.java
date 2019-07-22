package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.logging.Handler;

import static com.zxc.zhanglj.common.widget.explosion.FallingParticle.PART_WH;

/**
 * Author:ZLJ
 * Date: 2019-07-20 12:31
 */
public class BooleanFactory extends ParticleFactory {
    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();
        int partW_Count = w / PART_WH;//横向个数
        int partH_Count = h / PART_WH;//竖向个数

        //每个粒子在对应的bitmap中所占的大小
        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;


        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) {
            for (int column = 0; column < partW_Count; column++) {
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);
                float x = bound.left + PART_WH * column;
                float y = bound.right + PART_WH * row;
                particles[row][column] = new BooleanParticle(color, x, y, bound);

            }
        }

        return particles;
    }
}
