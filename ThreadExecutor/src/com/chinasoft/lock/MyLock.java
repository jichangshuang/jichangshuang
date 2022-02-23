package com.chinasoft.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class MyLock implements Lock {

    AtomicReference<Thread> owner = new AtomicReference<>();

    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            waiters.add(Thread.currentThread());
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }

    }

    @Override
    public void unlock() {
        while (owner.compareAndSet(Thread.currentThread(), null)) {
            for (Object o : waiters.toArray()) {
                Thread o1 = (Thread) o;
                LockSupport.unpark(o1);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
