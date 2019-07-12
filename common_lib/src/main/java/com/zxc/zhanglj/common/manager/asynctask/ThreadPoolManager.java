package com.zxc.zhanglj.common.manager.asynctask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kk on 2017/4/21.
 */

public class ThreadPoolManager {

    private static ThreadPoolManager instance;

    /**
     * 任务队列的线程池
     */
    private ExecutorService msgConvertExecutor;

    private ExecutorService convMergeExecutor;

    private ExecutorService taskExecutor;

    private ExecutorService fixedThreadPool;

    private ExecutorService calculateExecutor;

    private ExecutorService singleTaskExecutor;

    private ExecutorService playerTaskExecutor;

    private ThreadPoolManager(){}

    public static ThreadPoolManager getInstance(){
        if(instance == null){
            instance = new ThreadPoolManager();
        }

        return instance;
    }

    public ExecutorService getSingleExecutor() {
        if(singleTaskExecutor == null || singleTaskExecutor.isShutdown() || singleTaskExecutor.isTerminated()){
            singleTaskExecutor = Executors.newSingleThreadExecutor();
        }

        return singleTaskExecutor;
    }

    public ExecutorService getPlayerExecutor() {
        if(playerTaskExecutor == null || playerTaskExecutor.isShutdown() || playerTaskExecutor.isTerminated()){
            playerTaskExecutor = Executors.newSingleThreadExecutor();
        }

        return playerTaskExecutor;
    }

    public ExecutorService getMsgConvertExecutor() {
        if(msgConvertExecutor == null || msgConvertExecutor.isShutdown() || msgConvertExecutor.isTerminated()){
            msgConvertExecutor = Executors.newSingleThreadExecutor();
        }

        return msgConvertExecutor;
    }

    public ExecutorService getConvMergeExecutor() {
        if(convMergeExecutor == null || convMergeExecutor.isShutdown() || convMergeExecutor.isTerminated()){
            convMergeExecutor = Executors.newSingleThreadExecutor();
        }

        return convMergeExecutor;
    }

    public ExecutorService getCalculateExecutor() {
        if(calculateExecutor == null || calculateExecutor.isShutdown() || calculateExecutor.isTerminated()){
            calculateExecutor = Executors.newSingleThreadExecutor();
        }

        return calculateExecutor;
    }

    /**
     * 获取任务队列的线程池
     * @return
     */
    public ExecutorService getYaoTaskExecutor(){
        if(taskExecutor == null || taskExecutor.isShutdown() || taskExecutor.isTerminated()){
            taskExecutor = Executors.newSingleThreadExecutor();
        }

        return taskExecutor;
    }

    /**
     * 获取任务队列的线程池
     * @return
     */
    public ExecutorService getYaoTaskPoolExecutor(){
        if(fixedThreadPool == null || fixedThreadPool.isShutdown() || fixedThreadPool.isTerminated()){
            fixedThreadPool = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * 2 >= 5 ? 5 : Runtime.getRuntime().availableProcessors() * 2
            );
        }

        return fixedThreadPool;
    }

}
