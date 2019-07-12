package com.zxc.zhanglj.common.widget.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zxc.zhanglj.common.base.application.BaseAppliaction;
import com.zxc.zhanglj.commone_lib.R;
import com.zxc.zhanglj.utils.AppUtils;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.StringUtil;
import com.zxc.zhanglj.utils.WeakHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 仿微信新消息屏幕顶部下滑通知
 * Description: Notification And Animation Manager 管理Notification和展示动画
 * author: WangKunHui
 * date: 16/11/12 下午4:13
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public class NAManager implements WeakHandler.IHandler {

    private static String TAG = "NAManager";

    public static int TYPE_DEFAULT = 0;

    /**
     * 游戏类型
     */
    public static int TYPE_GAME = 1;

    /**
     * 是否对队列进行阻塞 如果为true 当通知栏在主界面展示时，将不再展示新的通知动画，只展示通知栏提示
     */
    private static NAManager instance;

    private WindowManager windowManager;

    private NotificationManager notificationManager;

    /**
     * windowManager是否正在展示
     */
    private boolean isShowing;

    /**
     * android 6.0以上需要授权才能用windowManager暂时
     */
    private static boolean isPermission = false;

    /**
     * 是否在锁屏状态，锁屏状态不用windowManager提示
     */
    private static boolean isLockScreen = false;

    /**
     * notification title和code的关系
     */
    private Map<String, Integer> userAndCodeMap = new HashMap<>();

    private NotificationTask currentTask;

    private WindowManager.LayoutParams layoutParams;

    /**
     * 过场动画市场
     */
    private int durationTime = 300;

    /**
     * 动画展示市场
     */
    private int showDurationTime = 3000;

    /**
     * 展示时间倒计时
     */
    private Timer countDownTimer;

    private SimpleDateFormat format;

    /**
     * 触发消失动画
     */
    private final int CANCEL_ANIMATION = 1;

    /**
     * chat notification code 相同的conversationId共用一个code
     */
    private int codeIndex = 0;

//    /**
//     * 共用消息类型的code  每次递增
//     */
//    private int commonCodeIndex = 5000;

    private WeakHandler handler = new WeakHandler(this);

    private static boolean isInitFinish = false;

    //是否开始展示
    private boolean isBeginShow = false;

    private static NotificationCache notificationCache;

    /**
     * 开始展示通知
     *
     * @param context
     */
    public void begin(Context context) {
        isBeginShow = true;

        if (notificationCache != null) {
            setNotificationData(context,
                    notificationCache.getTitle(),
                    notificationCache.getDescription(),
                    notificationCache.getImage(),
                    notificationCache.getIntent(),
                    notificationCache.getCode(),
                    notificationCache.isVoiceAlert(),
                    notificationCache.isVibrateAlert());
            notificationCache = null;
        }
    }

    /**
     * 要展示的消息队列
     */
    private NAManager(Context context) {

        format = new SimpleDateFormat("HH:mm");

        if (context != null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            initParams();

            isInitFinish = true;
        }
    }

    private void initParams() {
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.format = -3;
        layoutParams.gravity = Gravity.CENTER | Gravity.TOP;
        layoutParams.alpha = 1f;
    }

    /**
     * 设置notification参数
     *
     * @param context
     * @param title
     * @param description
     * @param image
     * @param intent
     * @param code
     */
    private void setNotificationData(final Context context, final String title, final String description, final String image, Intent intent, final int code, final boolean voiceAlert, final boolean vibrateAlert) {

        if (!isBeginShow) {
            if (notificationCache == null) {
                notificationCache = new NotificationCache(title, description, image, intent, code, voiceAlert, vibrateAlert);
            }
            return;
        }

        try {
            Date date = new Date();
            final String timeFormat = format.format(date);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(title).setContentText(description);
            builder.setContentIntent(pendingIntent);
            builder.setTicker("新消息")//通知首次出现在通知栏，带上升动画效果的
                    .setWhen(date.getTime())//通知产生的时间，会在通知信息里显示
                    .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                    .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
//                    .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                    .setSmallIcon(R.mipmap.ic_menu_me_normal)
                    .setLights(Color.GREEN, 300, 1000);

            if (!StringUtil.isEmpty(image)) {
                Glide.with(context).load(image).asBitmap().override(132, 132).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if (resource != null) {
                                    builder.setLargeIcon(resource);
                                } else {
                                    builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_menu_me_normal));
                                }

                                showNotification(builder, code, context, image, title, description, timeFormat, voiceAlert, vibrateAlert);
                            }
                        });
            } else {
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_menu_me_normal));
                showNotification(builder, code, context, image, title, description, timeFormat, voiceAlert, vibrateAlert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(NotificationCompat.Builder builder, int code, Context context, String image, String title, String description, String timeFormat, boolean voiceAlert, boolean vibrateAlert) {

        if (notificationManager == null) {
            return;
        }

        Notification notification = builder.build();

        if (voiceAlert) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }

        if (vibrateAlert) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(code, notification);

        NotificationTask task = new NotificationTask();

        if (!isLockScreen && !isShowing) {
            AnimationNotificationContainer container = new AnimationNotificationContainer(context);
            container.setData(image, title, description, timeFormat);
            task.setContainer(container);

            task.setCode(code);
            task.setNotification(notification);

            showNotification(task);
        } else {
            task.setContainer(null);

            task.setCode(code);
            task.setNotification(notification);
        }
    }

    public void setNotificationData(Context context, String title, String description, String image, Intent intent) {
        codeIndex++;
        setNotificationData(context, title, description, image, intent, codeIndex, true, true);
    }
    /**
     * 发送游戏通知
     *
     * @param context
     * @param title
     * @param description
     * @param image
     * @param intent
     */
    public void setGameNotificationData(Context context, String title, String description, String image, Intent intent) {

        codeIndex++;
        setNotificationData(context, title, description, image, intent, codeIndex, true, true);
    }

    private void showNotification(NotificationTask task) {

        try {
            if (isPermission) {
                currentTask = task;
                AnimationNotificationContainer container = task.getContainer();

                if (container != null) {
                    windowManager.addView(container, layoutParams);

                    showViewWithAnimation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showViewWithAnimation() {

        if (currentTask != null && currentTask.getContainer() != null) {

            isShowing = true;

            AnimationNotificationContainer container = currentTask.getContainer();

            container.setListener(new AnimationNotificationContainer.OnActionListener() {
                @Override
                public void onDown() {
                    LogUtil.i(TAG, "");
                    cancelTimer();
                }

                @Override
                public void onClick() {
                    LogUtil.i(TAG, "点击进入");
                    if (currentTask != null && currentTask.getNotification() != null) {

                        final Notification notification = currentTask.getNotification();
                        final PendingIntent contentIntent = notification.contentIntent;
                        dismissCurrentShow(new AnimationListener() {
                            @Override
                            public void onFinish() {
                                try {
                                    contentIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        if (notificationManager != null) {
                            notificationManager.cancel(currentTask.getCode());
                        }

                    } else {
                        LogUtil.i(TAG, "currentTask is null");
                    }
                }

                @Override
                public void onSlideUp() {
                    LogUtil.i(TAG, "上划消失");
                    dismissCurrentShow();
                }

                @Override
                public void onTraversal(boolean isLeft) {
                    LogUtil.i(TAG, "横向消失 isLeft  :  " + isLeft);
                    dismissCurrentShow(isLeft);
                }
            });

            ObjectAnimator a = ObjectAnimator.ofFloat(container.getAnimationView(), "translationY", -300, 0);
            a.setDuration(durationTime);

            a.start();
            resetTimer();
        }
    }

    private void resetTimer() {

        countDownTimer = new Timer();
        countDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(CANCEL_ANIMATION);
            }
        }, showDurationTime);
    }

    private void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();

            countDownTimer = null;
        }
    }



    public void clear(String conversationId) {
        Integer integer = userAndCodeMap.get(conversationId);
        if (integer != null && notificationManager != null) {
            try {
                notificationManager.cancel(integer);
            } catch (Exception e) {
                LogUtil.e(TAG, "Error  : " + e.getMessage());
            }
        }
    }


    public void dismissCurrentShow() {

        dismissCurrentShow(null, null);

    }

    public void dismissCurrentShow(Boolean isLeft) {

        dismissCurrentShow(isLeft, null);

    }

    public void dismissCurrentShow(AnimationListener finishListener) {
        dismissCurrentShow(null, finishListener);
    }

    /**
     * @param isLeft null 默认 true  向左滑动消息  false 向右滑动消失
     */
    public void dismissCurrentShow(Boolean isLeft, final AnimationListener finishListener) {

        if (isShowing && currentTask != null && currentTask.getContainer() != null) {

            final AnimationNotificationContainer currentShowView = currentTask.getContainer();

            ObjectAnimator anim;

            if (isLeft == null) {
                anim = ObjectAnimator.ofFloat(currentShowView.getAnimationView(), "translationY", 0, -300);
            } else if (isLeft) {
                anim = ObjectAnimator.ofFloat(currentShowView.getAnimationView(), "translationX", 0, -1300);
            } else {
                anim = ObjectAnimator.ofFloat(currentShowView.getAnimationView(), "translationX", 0, 1300);
            }

            anim.setDuration(durationTime);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    if (finishListener != null) {
                        finishListener.onFinish();
                    }

                    //初始化数据
                    if (windowManager != null && currentShowView != null) {

                        try {
                            currentShowView.setListener(null);
                            windowManager.removeView(currentShowView);
                        } catch (Exception e) {
                            LogUtil.i(TAG, "" + e.getMessage());
                        }
                    }
                    isShowing = false;
                    currentTask = null;
                }
            });

            anim.start();
        }

    }

    public void clearNotificationById(String targetId) {
        Integer integer = userAndCodeMap.get(targetId);
        if (integer != null && notificationManager != null) {
            userAndCodeMap.remove(targetId);
            notificationManager.cancel(integer);
        }
    }

    /**
     * 清除缓存
     */
    public void release() {
        if (notificationManager != null) {
            try {
                notificationManager.cancelAll();
            } catch (Exception e) {
                LogUtil.i(TAG, "" + e.getMessage());
            }
        }

        userAndCodeMap.clear();
        instance = null;

    }

    /**
     * 构建单例模式
     *
     * @return
     */
    public static NAManager getInstance() {

        Context context = BaseAppliaction.getInstance();

        if (context != null) {
            KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            isLockScreen = mKeyguardManager.inKeyguardRestrictedInputMode();
            isPermission = true;
        }

        if (instance == null || !isInitFinish) {
            instance = new NAManager(context);
        }

        return instance;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case CANCEL_ANIMATION:
                dismissCurrentShow();
                break;
        }
    }

    public class NotificationTask {

        private int code;

        private Notification notification;

        private AnimationNotificationContainer container;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Notification getNotification() {
            return notification;
        }

        public void setNotification(Notification notification) {
            this.notification = notification;
        }

        public AnimationNotificationContainer getContainer() {
            return container;
        }

        public void setContainer(AnimationNotificationContainer container) {
            this.container = container;
        }
    }

    public interface AnimationListener {
        void onFinish();
    }
}
