package com.lock;

/**
 * Created by Cruise on 2017/4/25.
 * 读写锁的自我实现
 * 问题是：如果线程1请求读锁，线程2请求写锁，但此时被阻塞；线程1再次请求读锁，因为线程2阻塞，所以线程1也会被阻塞，造成死锁。
 */
public class ReadWriteLock {
    private int writers = 0;
    private int readers = 0;
    private int writerequest = 0;

    public synchronized void lockRead() throws InterruptedException {
        while(writers >0 || writerequest >0){
            wait();
        }
        readers++;
    }

    public synchronized void unLockRead(){
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writerequest++;
        while(readers>0 || writers>0){
            wait();
        }
        writers++;
        writerequest--;
    }

    public synchronized void unLockWrite(){
        writers--;
        notifyAll();
    }

}
