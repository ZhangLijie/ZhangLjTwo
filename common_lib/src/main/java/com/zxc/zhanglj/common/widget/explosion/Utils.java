package com.zxc.zhanglj.common.widget.explosion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;


import java.util.Random;

/**
 * Author:ZLJ
 * Date: 2019-07-20 11:24
 */
public class Utils {

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();
    public Utils(){

    }

    public static Bitmap creatBitmapFromView(View view){
        view.clearFocus();
        Bitmap bitmap = creatBitmapSafely(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888,1);
        if(bitmap !=null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap((Bitmap)null);
            }
        }
        return bitmap;
    }

    private static Bitmap creatBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {

        try{
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError var5){
            var5.printStackTrace();
            if(retryCount>0){
                System.gc();
                return creatBitmapSafely(width,height,config,retryCount-1);
            }else{
                return null;
            }
        }

    }
}
