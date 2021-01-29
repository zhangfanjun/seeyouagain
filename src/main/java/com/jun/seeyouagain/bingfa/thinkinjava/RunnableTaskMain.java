package com.jun.seeyouagain.bingfa.thinkinjava;

public class RunnableTaskMain {
    public static void main(String[] args) {
//        runOtherOneThread();
        runOtherManyThread();
    }
    /**
     * 从结果可以看到t.start();执行之后，主线程就输出了，这是因为不同线程执行相互不影响
     */
    private static void runOtherOneThread() {
        Thread t = new Thread(new RunnableTask());
        t.start();
        System.out.println("主线程1");//任意一个线程都是可以开启另一个线程
    }
    /**
     * taskCount是静态变量，只初始化一次，所有的类的共享资源，
     * 每次new对象，都会初始化各自的常量id，这个时候静态变量出现加1的操作
     * 如果多个线程创建LiftOff就会出现id相同的情况，因为并发，每个对象都拿到相同的taskCount
     */
    private static void runOtherManyThread() {
        for (int i = 0; i < 5; i++) {
            new Thread(new RunnableTask()).start();
        }
        System.out.println("主线程2");
    }

}
