package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DaemonTaskMain {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new RunnableTask());
        t.setDaemon(true);
        t.start();
        System.out.println("后台线程开始执行");
        //通过调节main线程的睡眠时间，就能看到后台线程的打印不同结果
        TimeUnit.MILLISECONDS.sleep(5);
    }
}
