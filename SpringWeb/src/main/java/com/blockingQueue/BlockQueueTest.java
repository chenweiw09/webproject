package com.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Cruise on 2017/5/15.
 */
public class BlockQueueTest {
    public static void main(String[] args) {
        final ArrayBlockingQueue qu = new ArrayBlockingQueue(10);
        for(int i=0;i<50;i++){
            final int j = i;
            Thread th = new Thread(new Runnable() {
                public void run() {
                    System.out.println("thread"+j+" put working");
                    try {
                        qu.add(j);
                        System.out.println("queue size:"+qu.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
        }

        for(int i=0;i<5;i++){
            final int j = i;
            Thread th = new Thread(new Runnable() {
                public void run() {
                    System.out.println("thread"+j+" rm working ");
                    try {
                        qu.remove();
                        System.out.println("queue size:"+qu.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            th.start();
        }


    }
}
