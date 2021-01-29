package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorMain {
    public static void main(String[] args) {
        useCachedThreadPool();
        userFixThreadPool();

    }

    private static void useCachedThreadPool() {
        /**
         * 从这里可以看出是一个静态方法创建，单个Executor创建和管理系统中所有的任务
         *
         * 底层是最小0个线程数，最大Integer.MAX_VALUE，存活时间是1分钟
         * */
        ExecutorService exec = Executors.newCachedThreadPool();
        executeTask(exec);
    }

    private static void userFixThreadPool() {
        /**
         * 最小线程数和最大线程数都是指定的数量，存活时间为0，采用联表队列
         * */
        ExecutorService exec = Executors.newFixedThreadPool(5);
        executeTask(exec);
    }

    private static void userSingleThreadPool() {
        /**
         * 1，对于长期存活的任务非常有用，比如监听
         * 2，更新日志等小任务
         * 3，隐藏的悬挂任务队列，保证了任务执行的顺序性，改变了任务加锁的需求（异步执行，但是异步执行的任务有序，比如消息推送）
         * 4，文件系统，这个时候就能保证只有唯一个的一个任务执行，不会造成高并发的冲击，虽然在分布式上没有办法保证一个统一，但是也是一个方案
         * */
        ExecutorService exec = Executors.newSingleThreadExecutor();
        executeTask(exec);
    }

    private static void executeTask(ExecutorService exec) {
        for (int i = 0; i < 5; i++) {
            exec.execute(new RunnableTask());
        }
        exec.shutdown();//继续运行之前运行的任务，但是不再接收新任务
    }
}
