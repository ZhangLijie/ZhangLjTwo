package com.zxc.zhanglj.common.core.crash.reportlog;

import android.content.Context;

import com.zxc.zhanglj.common.core.crash.reportlog.collector.ReportData;
import com.zxc.zhanglj.common.core.crash.reportlog.collector.ReportDataCreater;
import com.zxc.zhanglj.common.core.crash.reportlog.persister.ReportFileManager;
import com.zxc.zhanglj.common.core.crash.reportlog.sender.ReportSender;
import com.zxc.zhanglj.common.manager.asynctask.YaoTaskExecutor;
import com.zxc.zhanglj.common.manager.asynctask.YaoTaskManager;

/**
 * 日志上报管理。
 * 全局管理类
 */
public class ReportManager {

    /**
     * 由使用者自己实现的发送器
     */
    private ReportSender sender;

    private static ReportManager sInstance;

    private ReportManager() {
    }

    public static synchronized ReportManager getInstance() {
        if (sInstance == null) {
            synchronized (ReportManager.class) {
                if (sInstance == null) {
                    sInstance = new ReportManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化管理器，包括初始化参数，初始化CrashHandler,初始化发送器
     * @param context
     */
    public void init(Context context, ReportSender sender) {
        NewCrashHandler.getInstance(context);
        if (sender == null) {
            throw new IllegalArgumentException("ReportSender can not null!!!");
        }
        this.sender = sender;

    }

    /**
     * 开始上报数据
     */
    public void startReport(final ReportDataCreater dataCreater) {

        YaoTaskManager.getInstance().addTaskPool(new YaoTaskExecutor<Void>() {
            @Override
            public Void exec() throws Exception {
                try {
                    if (dataCreater != null) {
                        SendTask sendTask = new SendTask(sender);
                        sendTask.setDataCreater(dataCreater);
                        sendTask.run();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onMainSuccess(Void result){
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    /**
     * 开始上报数据；上报未发送成功的历史数据
     */
    public void startReport() {
        this.startReport(null);
    }

    /**
     * 上报失败时，调用此接口，可以重新储存失败数据，以便下次重新上传
     * @param content
     */
    public void reSaveForReportFail(ReportData content) {
        if (content == null) {
            return;
        }
        ReportFileManager.reSaveToFile(content);
    }

}
