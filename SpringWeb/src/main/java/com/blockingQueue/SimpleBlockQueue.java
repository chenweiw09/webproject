package com.blockingQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Cruise on 2017/5/9.
 */
public class SimpleBlockQueue {

    private int size = 0;
    private List<Object> list = null;

    public SimpleBlockQueue(int size){
        this.size = size;
        list = new ArrayList<Object>();
    }

    public synchronized void putValue(Object value) throws InterruptedException {
        while(list.size() == size){
            wait();
        }

        if(list.size() <size){
            notifyAll();
        }
        list.add(value);
    }

    public synchronized void rmValue(Object value) throws InterruptedException {
        while(list.size() == 0){
            wait();
        }

        if(list.size() < size){
            notifyAll();
        }

        list.remove(0);
    }

    public synchronized int getSize(){
        return list.size();
    }

}
