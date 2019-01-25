package com.zxc.zhanglj.zhangljfirst.manager.event;


/**
 * tab切换时发送的EventBus
 */
public class SwitchTabEvent {
    /**
     * 切换前
     */
    public int lastType;
    /**
     * 切换后
     */
    public int targetType;

    public SwitchTabEvent(int lastType, int targetType){
        this.lastType = lastType;
        this.targetType = targetType;
    }

}
