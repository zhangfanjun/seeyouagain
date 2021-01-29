package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class DaemFactory implements ThreadFactory {
    /**
     * 返回一个设置了后台的线程
     * */
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool(new DaemFactory());
        pool.execute(new RunnableTask());
        System.out.println("后台线程开始执行");
        //通过调节main线程的睡眠时间，就能看到后台线程的打印不同结果
        TimeUnit.MILLISECONDS.sleep(5);
        pool.shutdown();
    }
}
