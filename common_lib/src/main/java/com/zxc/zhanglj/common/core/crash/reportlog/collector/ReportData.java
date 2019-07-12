package com.zxc.zhanglj.common.core.crash.reportlog.collector;

import java.util.HashMap;

/**
 * 继承自EnumMap<String, String>，方便使用
 */
public final class ReportData extends HashMap<String, String> {

    private static final long serialVersionUID = 4813885976930086141L;

    /**
     * 日志类型的名字，不同日志自己定义，默认叫“errorLog”
     */
    private String logName = "ErrorLog";

    /**
     * 上报数据的String版本，便于发送
     */
    private String stringData;

    /**
     * 设置日志类型名称，默认为“errorLog”
     * @param name
     */
    public void setLogType(String name) {
        this.logName = name;
    }

    /**
     * 获取日志类型名称
     * @return
     */
    public String getLogType() {
        return this.logName;
    }

    /**
     * 设置String类型的上报数据，便于上传服务器
     * @param data
     */
    public void setDataForSend(String data) {
        this.stringData = data;
    }

    /**
     * 获取String类型的上报数据，便于上传服务器
     * @return
     */
    public String getDataForSend() {
        return this.stringData;
    }

}
