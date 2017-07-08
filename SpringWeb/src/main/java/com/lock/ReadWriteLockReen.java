package com.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cruise on 2017/4/25.
 * 可重入的读写锁
 */
public class ReadWriteLockReen {
    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();
    private int writerrequest = 0;
    private int writeAccesses    = 0;
    private Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while(!canGetAccess(currentThread)){
            wait();
        }

        readingThreads.put(currentThread, getTimes(currentThread)+1);
    }

    public synchronized void lockWrite() throws InterruptedException {
        writerrequest++;
        Thread currentThread = Thread.currentThread();
        while(!canGetWriteAccess(currentThread)){
            wait();
        }
        writerrequest--;
        writeAccesses++;
        writingThread = currentThread;
    }

    public synchronized void unLockRead(){
        Thread currentThread = Thread.currentThread();
        if(readingThreads.get(currentThread) == 1){
            readingThreads.remove(currentThread);
        }else{
            readingThreads.put(currentThread, getTimes(currentThread)-1);
        }

        notifyAll();
    }

    public synchronized void unLockWrite(){
        writeAccesses--;
        if(writeAccesses==0){
            writingThread = null;
        }
        notifyAll();

    }

    boolean canGetAccess(Thread thread){
        if(writeAccesses>0 || writerrequest>0)
            return false;
        if(readingThreads.get(thread) != null)
            return true;
        return true;
    }

    boolean canGetWriteAccess(Thread thread){
        if(readingThreads.size()>0)
            return false;
        if(writingThread == null)
            return true;
        if(writingThread != thread)
            return false;
        return true;
    }
    int getTimes(Thread thread){
        if(readingThreads.get(thread) == null)
            return 0;
        else
            return readingThreads.get(thread);
    }


}
