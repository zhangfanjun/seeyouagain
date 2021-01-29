package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinglePriorityTask implements Runnable {
    private int countDown = 5;
    private volatile  double d;
    private int priority;//优先级
    public SinglePriorityTask (int priority){
        this.priority = priority;
    }
    public String toString(){
        return Thread.currentThread()+":"+countDown;
    }
    @Override
    public void run() {
        //给当前线程设置优先级
        Thread.currentThread().setPriority(priority);
        while (true){
            for (int i = 0; i < 100000; i++) {
                d += (Math.PI+Math.E)/(double)1;
                //每1000，线程建议让出cpu时间片
                if (i%1000==0){
                    Thread.yield();
                }
            }
            //调用toString
            System.out.println(this);
            //实现五次循环
            if(--countDown==0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        //开启5个低优先级的线程，优先级采用Thread的静态常量，有10个级别
        for (int i = 0; i < 5; i++) {
            pool.execute(new SinglePriorityTask(Thread.MIN_PRIORITY));
        }
        pool.execute(new SinglePriorityTask(Thread.MAX_PRIORITY));
        pool.shutdown();
    }
}
