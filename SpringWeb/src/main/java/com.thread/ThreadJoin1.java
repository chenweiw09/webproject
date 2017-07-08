package com.thread;

/**
 * Created by Cruise on 2017/4/20.
 */
public class ThreadJoin1 {
    public static void main(String[] args) {
        final Thread thread1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread 1");
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread 2");
            }
        });

        final Thread thread3 = new Thread(new Runnable() {
            public void run() {
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread 3");
            }
        });

        thread2.start();
        thread3.start();

        thread1.start();
    }
}
