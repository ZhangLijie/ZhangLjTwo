package com.zxc.zhanglj.zhangljfirst.commons.stack;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;


import com.zxc.zhanglj.zhangljfirst.commons.config.AppConstants;
import com.zxc.zhanglj.zhangljfirst.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 管理Activity
 */
public class ActivityManager {

	/** * 视图管理器，用于完全退出 * * @return */
	public static ActivityManager getScreenManager() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	/** * 回收堆栈中指定的activity * * @param activity */
	public void popActivity(Activity activity) {
		try{
			if(AppConstants.isAppDebug()){
				LogUtil.d("ActivityManager","ActivityStackSize popActivity activity : "+activity);
				if(activity != null){
					LogUtil.d("ActivityManager","ActivityStackSize popActivity isFinishing : "+activity.isFinishing());
				}
			}

			if (activity != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					if(!activity.isFinishing() && !activity.isDestroyed()){
						LogUtil.d("ActivityManager","popActivity0 currentActivity="+activity);
						activity.finish();
					}
				}else if(!activity.isFinishing()){
					LogUtil.d("ActivityManager","popActivity1 currentActivity="+activity);
					activity.finish();
				}
				if(activityStack != null){
					activityStack.remove(activity);
				}
			}

			if(AppConstants.isAppDebug()){
				if(activityStack != null){
					LogUtil.d("ActivityManager","ActivityStackSize popActivity size : "+activityStack.size());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void popActivity(String activityClassName) {
		try{
			if (!TextUtils.isEmpty(activityClassName) && activityStack != null) {
				List<Activity> removeActicityList = new ArrayList<Activity>();
				for(Activity activity:activityStack){
					if(activity != null && activityClassName.equals(activity.getClass().getName())){
						removeActicityList.add(activity);
					}
				}

				for(int i=0;i<removeActicityList.size();i++){
					popActivity(removeActicityList.get(i));
				}

				removeActicityList.clear();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void popActivityForChatActivity(String... activities) {

		List<String> keepList = Arrays.asList(activities);

		try{
			if (activityStack != null) {
				List<Activity> removeActicityList = new ArrayList<Activity>();
				for(Activity activity : activityStack){
					if (activity != null && !keepList.contains(activity.getClass().getName())){
						removeActicityList.add(activity);
					}
				}

				for(int i=0;i<removeActicityList.size();i++){
					popActivity(removeActicityList.get(i));
				}

				removeActicityList.clear();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** * 获取堆栈的栈顶activity * * @return 栈顶activity */
	private Activity currentActivity() {
		Activity activity = null;
		try {
			if (activityStack != null && !activityStack.isEmpty()) {
				activity = activityStack.pop();
			}
			return activity;
		} catch (Exception ex) {
			System.out.println("ScreenManager:currentActivity---->"
					+ ex.getMessage());
			return activity;
		} finally {
			activity = null;
		}
	}

	public Activity getTopActivity() {
		Activity activity = null;
		try {
			if (activityStack != null && !activityStack.isEmpty()) {
				activity = activityStack.peek();
			}
			return activity;
		} catch (Exception ex) {
			System.out.println("ScreenManager:currentActivity---->"
					+ ex.getMessage());
			return activity;
		} finally {
			activity = null;
		}
	}

	public String getTopActivityName() {
		Activity activity = null;
		try {
			if (activityStack != null && !activityStack.isEmpty()) {
				activity = activityStack.peek();
			}
			if(activity != null){
				return activity.getClass().getName();
			}

		} catch (Exception ex) {
			System.out.println("ScreenManager:currentActivity---->"
					+ ex.getMessage());
			activity = null;
		} finally {
			activity = null;
		}

		return null;
	}

	/** * 将activity压入堆栈 * * @param activity * 需要压入堆栈的activity */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.push(activity);

		if(AppConstants.isAppDebug()){
			if(activityStack != null){
				LogUtil.d("ActivityManager","ActivityStackSize pushActivity size : "+activityStack.size());
			}
		}
	}

	/** * 回收堆栈中所有Activity */
	public void popAllActivity() {
		try {
			while (activityStack != null && !activityStack.isEmpty()) {
				Activity activity = currentActivity();
				if (activity != null) {
					popActivity(activity);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/** * 只保留最上面的Activity */
	public void popOtherActivity() {
		try {
			Activity currentActivity = null;
			if(activityStack != null && !activityStack.isEmpty()){
				currentActivity = currentActivity();
				if(currentActivity != null){
					activityStack.remove(currentActivity);
				}
			}
			while (activityStack != null && !activityStack.isEmpty()) {
				Activity activity = currentActivity();
				if (activity != null) {
					LogUtil.d("ActivityManager","currentActivity="+activity);
					popActivity(activity);
				}
			}
			if(currentActivity != null){
				pushActivity(currentActivity);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public int getActivityStackSize(){
		if(activityStack != null){
			return activityStack.size();
		}

		return 0;
	}

	private ActivityManager() {

	}

	private static Stack<Activity> activityStack;
	private static ActivityManager instance;
}