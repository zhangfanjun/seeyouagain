package com.jun.seeyouagain.bingfa.thinkinjava;

import org.apache.tomcat.jni.Time;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableTask implements Callable<String> {
    private int id ;

    public CallableTask(int id){
        this.id = id;
    }

    /**
     * 继承接口的泛型决定了返回值的类型
     * */
    @Override
    public String call() throws Exception {
        System.out.println("do:" + id);
//        Time.sleep(1000);
        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("done:" + id);
        return "#"+id;
    }
}
