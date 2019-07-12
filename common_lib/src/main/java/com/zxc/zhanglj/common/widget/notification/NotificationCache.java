package com.zxc.zhanglj.common.widget.notification;

import android.content.Intent;

/**
 * Author:WangKunHui
 * Date: 2017/7/19 18:08
 * Desc:
 * E-mail:life_artist@163.com
 */
public class NotificationCache {

    private String title;

    private String description;

    private String image;

    private Intent intent;

    private int code;

    private boolean voiceAlert;

    private boolean vibrateAlert;

    public NotificationCache(String title, String description, String image, Intent intent, int code, boolean voiceAlert, boolean vibrateAlert) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.intent = intent;
        this.code = code;
        this.voiceAlert = voiceAlert;
        this.vibrateAlert = vibrateAlert;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isVoiceAlert() {
        return voiceAlert;
    }

    public void setVoiceAlert(boolean voiceAlert) {
        this.voiceAlert = voiceAlert;
    }

    public boolean isVibrateAlert() {
        return vibrateAlert;
    }

    public void setVibrateAlert(boolean vibrateAlert) {
        this.vibrateAlert = vibrateAlert;
    }
}
