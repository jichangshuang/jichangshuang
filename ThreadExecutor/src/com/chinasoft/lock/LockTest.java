package com.chinasoft.lock;

public class LockTest {

    private static long i;

    private static MyLock lock = new MyLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(i);
    }

    public static void increase() {
        lock.lock();
        i++;
        lock.unlock();
    }
}
