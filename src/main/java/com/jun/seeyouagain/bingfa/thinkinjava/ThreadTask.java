package com.jun.seeyouagain.bingfa.thinkinjava;

import java.util.concurrent.TimeUnit;

import static sun.misc.Version.print;

public class ThreadTask extends Thread {
    private int d;
    public ThreadTask(String name ,int sleepTime){
        super(name);
        d = sleepTime;
        start();
    }

    public void run (){
        try {
            sleep(d);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupt?" + isInterrupted());
            return;
        }
        System.out.println(getName() + " is over");
    }

}
