package com.zxc.zhanglj.common.manager.asynctask;

import android.os.AsyncTask;
import android.os.Looper;


import java.util.concurrent.ExecutorService;

/**
 * Created by like on 16/11/25.
 */

public class YaoTaskManager {

    protected static final String TAG = "YaoTaskManager";

    private static YaoTaskManager taskManager;

    private YaoTaskManager(){

    }

    public static synchronized YaoTaskManager getInstance(){

        initTaskManager();

        return taskManager;
    }

    private static void initTaskManager(){
        synchronized (YaoTaskManager.class){
            if(taskManager == null){
                taskManager = new YaoTaskManager();
            }
        }
    }

    public <T> void addMsgConvertTask(final YaoTaskExecutor<T> exec){
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                    t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(ThreadPoolManager.getInstance().getMsgConvertExecutor());
    }

    public <T> void addConvMergeTask(final YaoTaskExecutor<T> exec){
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                    t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(ThreadPoolManager.getInstance().getConvMergeExecutor());
    }

    public <T> void addCalculateDataTask(final YaoTaskExecutor<T> exec) {
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                    t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(ThreadPoolManager.getInstance().getCalculateExecutor());
    }
    /**
     * 用于写入数据库的异步操作或需要排队执行的异步操作
     * @param exec
     * @param <T>
     */
    public <T> void addTask(final YaoTaskExecutor<T> exec){
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                   t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(ThreadPoolManager.getInstance().getYaoTaskExecutor());
    }

    /**
     * 用于异步操作
     * @param exec
     * @param <T>
     */
    public <T> void addTaskPool(final YaoTaskExecutor<T> exec){
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                    t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(ThreadPoolManager.getInstance().getYaoTaskPoolExecutor());
    }

    public <T> void addTask(final YaoTaskExecutor<T> exec, ExecutorService taskExecutor){
        if(exec == null){
            return;
        }

        if(!(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())){
            throw new RuntimeException("addTask must run on main thread");
        }

        if(taskExecutor == null || taskExecutor.isShutdown()){
            taskExecutor = ThreadPoolManager.getInstance().getYaoTaskExecutor();
        }

        new AsyncTask<Void, Void,T>(){

            @Override
            protected void onPreExecute() {
                exec.onMainPreExec();
            }

            @Override
            protected T doInBackground(Void... params) {

                T t = null;
                try {
                    t = exec.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return t;
            }

            @Override
            protected void onPostExecute(T result) {
                exec.onMainSuccess(result);
            }

            //onCancelled方法用于在取消执行中的任务时更改UI
            @Override
            protected void onCancelled() {
                exec.onMainFailed();
            }

        }.executeOnExecutor(taskExecutor);
    }
}
