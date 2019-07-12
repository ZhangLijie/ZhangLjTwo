package com.zxc.zhanglj.common.manager.asynctask;

/**
 * Created by kk on 2017/6/1.
 */

public interface IYaoTask <T>{
    void onMainPreExec();
    void onMainSuccess(T result);
    void onMainFailed();
}
