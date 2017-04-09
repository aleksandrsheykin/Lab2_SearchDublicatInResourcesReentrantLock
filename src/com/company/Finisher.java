package com.company;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by admin on 09.04.2017.
 */
public class Finisher extends Thread  {
    private AtomicBoolean[] threadsIsFinish;
    private long startTime = System.nanoTime();
    private long finishTime = 0;

    public Finisher(Integer length) {
        this.threadsIsFinish = new AtomicBoolean[length];
        for (int i=0; i<length; i++) {
            this.threadsIsFinish[i] = new AtomicBoolean();
            this.threadsIsFinish[i].set(false);
        }
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        for(AtomicBoolean a : threadsIsFinish) {
            if (!a.get()) {
                Thread.yield();
            }
        }
        finishTime = System.nanoTime();
        System.out.printf("Finish! time = ");
        System.out.println(finishTime-startTime);
    }

    public void addThread(Integer threadInd) {
        this.threadsIsFinish[threadInd].set(false);
    }

    public void finish(Integer threadInd){
        threadsIsFinish[threadInd].set(true);
        //Thread.yield();
        //notify();
    }

}
