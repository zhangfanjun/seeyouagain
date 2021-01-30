package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.TimeUnit;

public class RunnableTask implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;//静态变量，用于数量递增赋值给每个新new的对象
    private final int id = taskCount++;

    public RunnableTask() {

    }

    public RunnableTask(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "), ";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("后台线程中的finally是否被执行");
            }
            Thread.yield();//对线程调度器的一种建议，可以切换上下文到别的线程了
        }
    }
}
