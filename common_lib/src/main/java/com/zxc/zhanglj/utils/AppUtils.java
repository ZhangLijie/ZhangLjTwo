package com.zxc.zhanglj.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.zxc.zhanglj.common.config.AppConstants;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import java.util.List;


import static android.content.ContentValues.TAG;

/**
 * Created by zhanglijie
 * on 2016/10/17.
 */
@SuppressWarnings("unused")
public class AppUtils {

    private static final String PACKAGE_NAME = "com.zxc.zhanglj";

    /**
     * 指定小数输出
     *
     * @param s      输入
     * @param format 小数格式，比如保留两位0.00
     * @return 输出结果
     */
    public static String decimalFormat(double s, String format) {
        DecimalFormatSymbols d = new DecimalFormatSymbols();
        d.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(format, d);
        return decimalFormat.format(s);
    }

    public static String dataFormat(double s, int num) {
        BigDecimal b = new BigDecimal(s);
        BigDecimal temp = b.setScale(num, BigDecimal.ROUND_HALF_UP);
        return temp.stripTrailingZeros().toPlainString();
    }

    /**
     * 把Bitmap转Byte
     *
     * @param bitmap bitmap对象
     * @return Bytes
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

//    public static void initSystemBar(Activity activity) {
//
//        initSystemBar(activity, R.color.statusBarColor);
//
//    }
//
//    public static void initSystemBar(Activity activity, int colorRes) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(activity, true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//        tintManager.setStatusBarTintEnabled(true);
//        // 使用颜色资源
//        tintManager.setStatusBarTintResource(colorRes);
//    }


    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static String GetPhoneIP(Context context) {
        try {
            //获取wifi服务
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            //判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int i = wifiInfo.getIpAddress();
                return (i & 0xFF) + "." +
                        ((i >> 8) & 0xFF) + "." +
                        ((i >> 16) & 0xFF) + "." +
                        (i >> 24 & 0xFF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                try {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                    return ipAddress;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

//    /**
//     * 设置后台flag
//     *
//     * @param context
//     * @param isBackground
//     */
//    public static void setBackgroundFlag(Context context, boolean isBackground) {
//        if (isBackground) {
//            SharedPreferencesUtil.setBoolean(context.getApplicationContext(), AppConstants.APP_BACKGROUND_KEY, true);
//        } else {
//            SharedPreferencesUtil.setBoolean(context.getApplicationContext(), AppConstants.APP_BACKGROUND_KEY, false);
//        }
//    }
//
//    /**
//     * 是否退到后台
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isBackground(Context context) {
//        return SharedPreferencesUtil.getBoolean(context.getApplicationContext(), AppConstants.APP_BACKGROUND_KEY, true);
//    }


    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                LogUtil.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        LogUtil.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

//    public static void dealClickNotification(Context context, Bundle args) {
//        try {
//            //判断app进程是否存活
//            if (AppUtils.isAppAlive(context, PACKAGE_NAME)) {
//                LogUtil.i(TAG, "the app process is alive");
//                Intent mainIntent = new Intent(context, HongBaoMainActivity.class);
//                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                if (args != null) {
//                    mainIntent.putExtra(PushConstant.NOTIFICATION_BUNDLE, args);
//                }
//
//                Intent[] intents = new Intent[]{mainIntent};
//                context.startActivities(intents);
//            } else {
//                LogUtil.i(TAG, "the app process is dead");
//                Intent launchIntent = context.getPackageManager().
//                        getLaunchIntentForPackage(PACKAGE_NAME);
//                launchIntent.setFlags(
//                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//
//                if (args != null) {
//                    launchIntent.putExtra(PushConstant.NOTIFICATION_BUNDLE, args);
//                }
//                context.startActivity(launchIntent);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static final boolean haSDCardWritePermission(Context context) {
//        boolean isSuc = true;
//        try {
//            String tempPath = FileUtils.getRootPath(context) + "temp_sdcard_write.txt";
//            isSuc = FileUtils.isCanCreateFile(tempPath);
//
//            if (isSuc) {
//                new File(tempPath).delete();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return isSuc;
//    }

    /**
     * 关闭键盘
     *
     * @param activity Activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null)
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return TextUtils.isEmpty(packageInfo.versionName) ? "" : packageInfo.versionName;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前应用版本序号
     *
     * @param context
     * @return 当前应用版本序号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 打开并安装apk
     *
     * @param context
     * @param path
     */
    public static void openAndInstallApk(Context context, String path) {

        try {
            File file = new File(path);

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showToast(context, "请到应用宝搜索电视红包下载最新版本");
            e.printStackTrace();
        }

    }

    public static String getPackageName(Context context) {

        return context.getPackageName();

    }
//
//    public static boolean isServiceWorked(Context context) {
//        try {
//            ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            if (myManager != null) {
//                List<ActivityManager.RunningServiceInfo> runningService = myManager.getRunningServices(40);
//                if (runningService == null || runningService.size() == 0) {
//                    return false;
//                }
//                for (int i = 0; i < runningService.size(); i++) {
//                    if (runningService.get(i) != null &&
//                            runningService.get(i).service != null &&
//                            YaoService.class.getName().equals(
//                                    runningService.get(i).service.getClassName().toString())) {
//
//                        LogUtil.i(TAG, "YaoService worked");
//
//                        return true;
//                    }
//                }
//
//                LogUtil.i(TAG, "YaoService no worked");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

//    /**
//     * md5加密
//     *
//     * @param macaddress
//     * @return
//     */
//    public static String md5Sign(String macaddress, String uid) {
//        if (TextUtils.isEmpty(macaddress)) {
//            throw new RuntimeException("ms addr is null");
//        }
//
//        if (TextUtils.isEmpty(uid)) {
//            throw new RuntimeException("ms id is null");
//        }
//
//        String reg = JniUtil.getInstance().getUidInfo(macaddress, uid, AppConstants.APP_SECRET);
//
//        String info = MD5.md5(reg);
//
//        LogUtil.d("AppEncrypt", "ms : " + reg + "==========info : " + info);
//
//        return info;
//    }


    /**
     * 获取meta－data的值
     */
    public static Object getMetaData(Context context, String key) {

        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);

            return ai.metaData.get(key);
        } catch (Exception e) {

            LogUtil.e("AppUtils", "getMetaData error : " + e.toString());
            LogUtil.e("AppUtils", "getMetaData error，key : " + key);
        }

        return false;

    }

//    /**
//     * 自动注销
//     *
//     * @param context
//     */
//    public static void autoKicked(final Context context) {
//        UserLoginActivity.launchActivity(context);
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                AppUtils.logout(context, true, false);
//            }
//        }, 1000);
//    }
//
//    /**
//     * 应用注销
//     */
//    public static void logout(final Context context, final boolean isDel, final boolean isCloseCurrentActivity) {
//        try {
//
//            TokenManager.getInstance().setAndDecodeToken(context,null);
//
//            NAManager.getInstance().release();
//
//            YaoPushManager.unBindPush(context);
//            IMManager.getInstance().disconnection();
//
//            YaoTaskManager.getInstance().addTask(new YaoTaskExecutor<String>() {
//                @Override
//                public String exec() throws Exception {
//
//                    WebUtil.removeCookie(context);
//                    WebUtil.clearWebCache(context);
//                    FileUtils.clearCache(context);
//
//                    if (LocalUserModelManager.getInstance().getCachedUserModel() != null) {
//                        // 用户下次再登录的时候要显示摇界面的引导
//                        SharedPreferencesUtil.setShowMainGuideFlag(context, 0, LocalUserModelManager.getInstance().getCachedUserModel().getTvmid());
//                        // 标记用户已注销
//                        LocalUserModelManager.getInstance().getCachedUserModel().setExit(true);
//                        UserModelDBTool.saveOrUpdateUserModel(LocalUserModelManager.getInstance().getCachedUserModel());
//                        if (isDel) {
////                    UserModelManager.deleteUserModelById(LocalUserModelCache.getInstance().getCachedUserModel());
////                    PhoneContactDbUtilsManager.clearTable();
////                    AddMsgDbUtilsManager.clearTable();
////                    SharedPreferencesUtil.removeString(context,SharedPreferencesUtil.NEW_FRIEND_TIME_KEY);
////                    ConversationManager.clearTable();
//                        }
//                    }
//
//                    return null;
//                }
//
//                public void onMainSuccess(String result){
//                    BindManager.getInstance().destroy();
//
//                    DatabaseHelper.release();
//                    CommonDatabaseHelper.release();
//                    DatabaseHelperTemp.release();
//                    PushDataHelper.release();
//
//                    DeviceIdFactory.getInstance().destroy();
//
//                    LocalUserModelManager.getInstance().setCachedUserModel(null);
//
//                    if (isCloseCurrentActivity) {
//                        com.tvmining.yao8.commons.manager.stack.ActivityManager.getScreenManager().popAllActivity();
//                    } else {
//                        com.tvmining.yao8.commons.manager.stack.ActivityManager.getScreenManager().popOtherActivity();
//                    }
//
//                    SyncJobManager.getInstance().cancelAll(context);
//
//                    LocationTool.getInstance().resetLastTime();
//
//                    EventBusManager.getInstance().post(new UserLogoutEvent());
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 解决InputMethodManager.mnextServedView导致HtmlActivity内存溢出的问题
     *
     * @param destContext
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        try {
            if (destContext == null) {
                return;
            }

            InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return;
            }

            String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
            Field filed;
            Object filedObject;

            for (String view : viewArray) {
                try {
                    filed = inputMethodManager.getClass().getDeclaredField(view);
                    if (!filed.isAccessible()) {
                        filed.setAccessible(true);
                    }
                    filedObject = filed.get(inputMethodManager);
                    if (filedObject != null && filedObject instanceof View) {
                        View fileView = (View) filedObject;
                        if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                            filed.set(inputMethodManager, null); // 置空，破坏掉path to gc节点
                        } else {
                            break;// 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        }
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fix for https://code.google.com/p/android/issues/detail?id=171190 .
     * <p>
     * When a view that has focus gets detached, we wait for the main thread to be idle and then
     * check if the InputMethodManager is leaking a view. If yes, we tell it that the decor view got
     * focus, which is what happens if you press home and come back from recent apps. This replaces
     * the reference to the detached view with a reference to the decor view.
     * <p>
     * Should be called from {@link Activity#(android.os.Bundle)} )}.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void fixFocusedViewLeak(Application application) {

        // Don't know about other versions yet.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 || Build.VERSION.SDK_INT > 23) {
            return;
        }

        final InputMethodManager inputMethodManager =
                (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);

        final Field mServedViewField;
        final Field mHField;
        final Method finishInputLockedMethod;
        final Method focusInMethod;
        try {
            mServedViewField = InputMethodManager.class.getDeclaredField("mServedView");
            mServedViewField.setAccessible(true);
            mHField = InputMethodManager.class.getDeclaredField("mServedView");
            mHField.setAccessible(true);
            finishInputLockedMethod = InputMethodManager.class.getDeclaredMethod("finishInputLocked");
            finishInputLockedMethod.setAccessible(true);
            focusInMethod = InputMethodManager.class.getDeclaredMethod("focusIn", View.class);
            focusInMethod.setAccessible(true);
        } catch (NoSuchMethodException | NoSuchFieldException unexpected) {
            LogUtil.e("IMMLeaks", "Unexpected reflection exception", unexpected);
            return;
        }

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityDestroyed(Activity activity) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ReferenceCleaner cleaner = new ReferenceCleaner(inputMethodManager, mHField, mServedViewField,
                        finishInputLockedMethod);
                View rootView = activity.getWindow().getDecorView().getRootView();
                ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
                viewTreeObserver.addOnGlobalFocusChangeListener(cleaner);
            }
        });
    }

    static class ReferenceCleaner
            implements MessageQueue.IdleHandler, View.OnAttachStateChangeListener,
            ViewTreeObserver.OnGlobalFocusChangeListener {

        private final InputMethodManager inputMethodManager;
        private final Field mHField;
        private final Field mServedViewField;
        private final Method finishInputLockedMethod;

        ReferenceCleaner(InputMethodManager inputMethodManager, Field mHField, Field mServedViewField,
                         Method finishInputLockedMethod) {
            this.inputMethodManager = inputMethodManager;
            this.mHField = mHField;
            this.mServedViewField = mServedViewField;
            this.finishInputLockedMethod = finishInputLockedMethod;
        }

        @Override
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (newFocus == null) {
                return;
            }
            if (oldFocus != null) {
                oldFocus.removeOnAttachStateChangeListener(this);
            }
            Looper.myQueue().removeIdleHandler(this);
            newFocus.addOnAttachStateChangeListener(this);
        }

        @Override
        public void onViewAttachedToWindow(View v) {
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            v.removeOnAttachStateChangeListener(this);
            Looper.myQueue().removeIdleHandler(this);
            Looper.myQueue().addIdleHandler(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean queueIdle() {
            clearInputMethodManagerLeak();
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void clearInputMethodManagerLeak() {
            try {
                Object lock = mHField.get(inputMethodManager);
                // This is highly dependent on the InputMethodManager implementation.
                synchronized (lock) {
                    View servedView = (View) mServedViewField.get(inputMethodManager);
                    if (servedView != null) {

                        boolean servedViewAttached = servedView.getWindowVisibility() != View.GONE;

                        if (servedViewAttached) {
                            // The view held by the IMM was replaced without a global focus change. Let's make
                            // sure we get notified when that view detaches.

                            // Avoid double registration.
                            servedView.removeOnAttachStateChangeListener(this);
                            servedView.addOnAttachStateChangeListener(this);
                        } else {
                            // servedView is not attached. InputMethodManager is being stupid!
                            Activity activity = extractActivity(servedView.getContext());
                            if (activity == null || activity.getWindow() == null) {
                                // Unlikely case. Let's finish the input anyways.
                                finishInputLockedMethod.invoke(inputMethodManager);
                            } else {
                                View decorView = activity.getWindow().peekDecorView();
                                boolean windowAttached = decorView.getWindowVisibility() != View.GONE;
                                if (!windowAttached) {
                                    finishInputLockedMethod.invoke(inputMethodManager);
                                } else {
                                    decorView.requestFocusFromTouch();
                                }
                            }
                        }
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException unexpected) {
                LogUtil.e("IMMLeaks", "Unexpected reflection exception", unexpected);
            }
        }

        private Activity extractActivity(Context context) {
            if (context instanceof Application) {
                return null;
            } else if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                Context baseContext = ((ContextWrapper) context).getBaseContext();
                // Prevent Stack Overflow.
                if (baseContext == context) {
                    return null;
                }
                context = baseContext;
            } else {
                return null;
            }

            return null;
        }
    }

    public static boolean checkFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            Log.e(TAG, "Below API 19 cannot invoke!");
        }
        return false;
    }


}
