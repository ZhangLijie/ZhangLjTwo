package com.zxc.zhanglj.common.manager.asynctask;

/**
 * Created by kk on 2017/6/1.
 */

public abstract class YaoTaskExecutor<T> implements IYaoTask<T> {

    public static final String TAG_CHECK_SERVICE = "checkService";
    public static final String TAG_QUERY_CATEGORY_MESSAGE = "queryCategoryMessage";
    public static final String TAG_QUERY_CATEGORY_PERSONAL = "queryCategoryPersonal";
    public static final String TAG_SHOW_NOTIFACATION_PERMISSION_DIALOG = "showNotifacationPermissionDialog";

    private String tag;
    private boolean isCanel;

    public String getTag() {
        return tag;
    }

    public YaoTaskExecutor setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public boolean isCanel() {
        return isCanel;
    }

    public YaoTaskExecutor canel() {
        isCanel = true;
        return this;
    }

    public abstract T exec() throws Exception;
    public void onMainPreExec(){}
    public void onMainSuccess(T result){}
    public void onMainFailed(){}
}
