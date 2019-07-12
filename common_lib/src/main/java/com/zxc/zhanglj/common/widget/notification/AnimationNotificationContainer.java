package com.zxc.zhanglj.common.widget.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxc.zhanglj.commone_lib.R;
import com.zxc.zhanglj.utils.LogUtil;
import com.zxc.zhanglj.utils.RegexUtil;
import com.zxc.zhanglj.utils.StringUtil;

/**
 * Description: notification通知动画的容器
 * author: WangKunHui
 * date: 16/11/12 下午3:17
 * <p>
 * Copyright©2016 by wang. All rights reserved.
 */
public class AnimationNotificationContainer extends RelativeLayout {

    private String TAG = "NotificationContainer";

    private float rawX = 0;

    private float rawY = 0;

    private float touchX = 0;

    private float startY = 0;

    private RelativeLayout animationView;

    private OnActionListener listener;

    private ImageView headImage;

    private TextView title;

    private TextView content;

    private TextView time;

    public AnimationNotificationContainer(Context context) {
        this(context, null);
    }

    public AnimationNotificationContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationNotificationContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.widget_notification_animation_container, this);

        findViews();
    }


    public void setListener(OnActionListener listener) {
        this.listener = listener;
    }

    private void findViews() {
        animationView = (RelativeLayout) findViewById(R.id.rl_animation_view);

        headImage = (ImageView) findViewById(R.id.iv_head_image);
        title = (TextView) findViewById(R.id.tv_title);
        content = (TextView) findViewById(R.id.tv_content);
        time = (TextView) findViewById(R.id.tv_time);
    }

    public View getAnimationView() {
        return animationView;
    }

    /**
     * 滑动方式
     */
    private ScrollOrientationEnum scrollOrientationEnum = ScrollOrientationEnum.NONE;

    public boolean onTouchEvent(MotionEvent event) {

        rawX = event.getRawX();
        rawY = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (listener != null) {
                    listener.onDown();
                }

                touchX = event.getX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                switch (scrollOrientationEnum) {
                    case NONE:
                        /**
                         * 判断是横向还是纵向滑动
                         */

                        /**
                         * 判断为左右滑动
                         */
                        if (Math.abs((rawX - touchX)) > 20) {
                            scrollOrientationEnum = ScrollOrientationEnum.HORIZONTAL;
                        }
                        /**
                         * 只能向上滑动
                         */
                        else if (startY - rawY > 20) {
                            scrollOrientationEnum = ScrollOrientationEnum.VERTICAL;
                        }

                        break;
                    case HORIZONTAL:
                        LogUtil.i(TAG, "左右滑动");
                        break;
                    case VERTICAL:
                        LogUtil.i(TAG, "上下滑动");
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (scrollOrientationEnum == ScrollOrientationEnum.NONE) {

                    if (listener != null) {
                        listener.onClick();
                    }

                    LogUtil.i(TAG, "点击事件");
                } else if (scrollOrientationEnum == ScrollOrientationEnum.HORIZONTAL) {
                    LogUtil.i(TAG, "左右滑动消失");

                    float leftX = event.getX();
                    float v = leftX - touchX;

                    boolean isLeft = false;
                    if (v > 0) {
                        isLeft = false;
                    } else {
                        isLeft = true;
                    }

                    if (listener != null) {
                        listener.onTraversal(isLeft);
                    }

                } else if (scrollOrientationEnum == ScrollOrientationEnum.VERTICAL) {
                    LogUtil.i(TAG, "向上滑动消失");
                    if (listener != null) {
                        listener.onSlideUp();
                    }
                }

                scrollOrientationEnum = ScrollOrientationEnum.NONE;
                break;

        }
        return super.onTouchEvent(event);
    }

    public void setData(String headImagePath, String title, String content, String date) {
        if (!StringUtil.isEmpty(headImagePath)) {
            Glide.with(getContext()).load(headImagePath).placeholder(R.mipmap.head_default)
                    .error(R.mipmap.head_default).into(headImage);
//            ImageLoaderBuilder.getBuilder(getContext()).load(AppUtils.getHeadImage(headImagePath))
//                    .loadingImage(R.mipmap.ic_logo)
//                    .errorImage(R.mipmap.ic_logo).into(headImage);
        } else {
            headImage.setImageResource(R.mipmap.head_default);
        }

        if (RegexUtil.regexALabel(content)) {
            content = RegexUtil.replaceLabel(content);
        }

        if (RegexUtil.regexExpression(content)) {
           // SpannableString spannableString = Str2EmjoyUtil.getSpannableString(content, getContext());
           // this.content.setText(spannableString);
        } else {
            this.content.setText(content);
        }

        if (RegexUtil.regexH5Label(content)) {
            content = RegexUtil.replaceH5Label(content);
        }

        this.title.setText(title);
        this.time.setText(date);
    }

    enum ScrollOrientationEnum {
        VERTICAL, HORIZONTAL, NONE, USELESS
    }

    interface OnActionListener {

        /**
         * 手指按下
         */
        public void onDown();

        /**
         * 点击事件
         */
        public void onClick();

        /**
         * 向上滑动
         */
        public void onSlideUp();

        /**
         * 横向移动
         */
        public void onTraversal(boolean isLeft);
    }
}
