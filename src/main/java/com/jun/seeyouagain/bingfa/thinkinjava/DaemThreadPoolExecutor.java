package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DaemThreadPoolExecutor extends ThreadPoolExecutor {

    public DaemThreadPoolExecutor(){
        super(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),new DaemFactory());
    }
}
