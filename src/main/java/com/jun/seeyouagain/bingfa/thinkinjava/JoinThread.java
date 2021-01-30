package com.jun.seeyouagain.bingfa.thinkinjava;

public class JoinThread extends Thread {
    private ThreadTask t;
    public JoinThread (String name,ThreadTask t){
        super(name);
        this.t = t;
        start();
    }

    public void run(){
        try {
            t.join();
        } catch (InterruptedException e) {
            System.out.println("interrupt");
        }
        System.out.println(getName() + " is completed");
    }

    public static void main(String[] args) {
        ThreadTask
                sleepy = new ThreadTask("sleep",1500),
                grumpy = new ThreadTask("grumpy",1500);
        JoinThread
                dopey = new JoinThread("dopey",sleepy),
                doc = new JoinThread("doc",grumpy);
        grumpy.interrupt();
    }
}
