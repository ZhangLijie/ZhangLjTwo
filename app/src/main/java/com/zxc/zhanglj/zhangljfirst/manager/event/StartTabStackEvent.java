package com.zxc.zhanglj.zhangljfirst.manager.event;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class StartTabStackEvent {
    public SupportFragment targetFragment;

    public StartTabStackEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
