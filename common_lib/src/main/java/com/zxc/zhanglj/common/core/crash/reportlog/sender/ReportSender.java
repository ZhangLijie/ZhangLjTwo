package com.zxc.zhanglj.common.core.crash.reportlog.sender;


import com.zxc.zhanglj.common.core.crash.reportlog.collector.ReportData;

/**
 * 日志上传的接口，不同上传方式自己实现
 */
public interface ReportSender {
    /**
     * 发送日志
     * @param errorContent
     *            上传的日志内容
     */
    public void send(ReportData errorContent);
}
