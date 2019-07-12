package com.zxc.zhanglj.common.core.crash.reportlog.collector;

import android.content.Context;

import com.zxc.zhanglj.common.core.crash.reportlog.ReportField;
import com.zxc.zhanglj.common.core.crash.utils.CrashSystemUtil;

/**
 * 用于创建错误日志内容的公共数据
 */
public abstract class ReportDataCreater {
    /**
     * 生成公共的上报数据项的内容
     * @param context
     * @return
     */
    protected ReportData createCommonInfo(Context context) {
        final ReportData reportInfo = new ReportData();

        if (context != null) {
            reportInfo.put(ReportField.APP_PACKAGE_NAME.name(),
                    CrashSystemUtil.getPackageName(context));
            reportInfo.put(ReportField.APP_VERSION_NAME.name(),
                    CrashSystemUtil.getVersionName(context));
            reportInfo.put(ReportField.APP_VERSION_CODE.name(),
                    String.valueOf(CrashSystemUtil.getVersionCode(context)));
        }

        // mac有时获取不到手机的
        reportInfo.put(ReportField.MAC.name(), CrashSystemUtil.getMacAddress());
        // 这个ip获取的实现有问题，可能不全
        reportInfo.put(
                ReportField.IP.name(),
                (CrashSystemUtil.getLocalIpAddress() != null ? CrashSystemUtil
                        .getLocalIpAddress() : "ip_null"));
//        reportInfo.put(ReportField.DEVICES_MODEL.name(),
//                DeviceUtils.getTerminalSeries());
        reportInfo.put(ReportField.ANDROID_VERSION.name(),
                CrashSystemUtil.getOSVersion());
//        reportInfo.put(ReportField.REPORT_DATE.name(), TimeUtils
//                .timeToStr(TimeUtils.longToTime(System.currentTimeMillis())));

        return reportInfo;
    }

    /**
     * 不同的错误上报类，填充不同的数据项。
     * 可以先调用{@code createCommonInfo}填充公共的数据项。
     * 建议使用方法：
     * <pre>
     * Override
     * public ReportData createInfo() {
     * ReportData reportData = this.createCommonInfo(this.context);
     * reportData.put("自定义的key", "自定义的value");
     * reportData.setLogType("自定义的日志类型");
     * return reportData;
     * }
     * </pre>
     */
    public abstract ReportData createData();

}
