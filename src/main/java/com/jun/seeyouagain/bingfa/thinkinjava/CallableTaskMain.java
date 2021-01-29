package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTaskMain {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        getResultFromFuture(pool);

    }

    private static void getResultFromFuture(ExecutorService pool) {
        /**
         * 想对比runnable任务的execute，callable任务用submit
         * submit会产生Future对象，
         * 可以用isDone来判断是否完成，也可以用get方法直接得到结果
         * Future还有线程等待的功能，可以实现线程之间的切换工作
         * */
        ArrayList<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futures.add(pool.submit(new CallableTask(i+1)));
        }
        System.out.println("开始获取结果");
        for (Future<String> future : futures) {
            try {
                System.out.println(future.get());
//                boolean cancel = future.cancel(true);
//                if (!cancel){
//                    System.out.println(future.get());
//                } else {
//                    System.out.println("被取消");
//                }
            } catch (InterruptedException | ExecutionException e) {
               e.printStackTrace();
                return;
            } finally {
                pool.shutdown();//注意这里一定要加finally，用于最后关闭前面的任务
            }
        }
    }
}
