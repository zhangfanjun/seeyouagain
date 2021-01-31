package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExceptionThread extends Thread {
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
//        catchExceptionThreadFactory();
        catchExecptionDefault();
    }

    private static void catchExecptionDefault() {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + ":" + e);
            }
        });
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(new ExceptionThread());
    }

    private static void catchExceptionThreadFactory() {
        try {
            ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread t, Throwable e) {
                            System.out.println(t.getName() + ":" + e);
                        }
                    });
                    return t;
                }
            });
            pool.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            System.out.println("捕捉异常");
        }
    }
}
