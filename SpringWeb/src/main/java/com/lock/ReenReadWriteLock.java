//package com.lock;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Cruise on 2017/5/4.
// */
//public class ReenReadWriteLock {
//
//    private int writeRequest = 0;
//    private int writeAccess;
//    private Map<Thread, Integer> map = new HashMap<Thread, Integer>();
//    private Thread writeThread = null;
//
//    public synchronized void lockRead() throws InterruptedException {
//        Thread callingThread = Thread.currentThread();
//        if(!canGetReadAccess(callingThread)){
//            wait();
//        }
//
//        map.put(callingThread, getReadAccessCnt(callingThread)+1);
//    }
//
//    private Integer getReadAccessCnt(Thread thread){
//        Integer cnt = map.get(thread);
//        if(cnt == null)
//            return 0;
//        return cnt.intValue();
//    }
//}
