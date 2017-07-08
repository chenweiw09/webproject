package com.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Cruise on 2017/4/24.
 * 利用读写锁实现缓存
 */
public class ReadWriteLockDemon {

    static Map<String, Object> map = new HashMap<String,Object>();
    static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static Object getData(String key){
        lock.readLock().lock();
        try {
            return map.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }


    public static void putData(String key, Object data){
        lock.writeLock().lock();
        try {
            map.put(key,data);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void clear(){
        lock.writeLock().lock();
        try {
            map.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
