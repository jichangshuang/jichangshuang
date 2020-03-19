package com.chinasoft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadExecutor {

    private List<WorkThread> workThreadList;
    private BlockingQueue<Runnable> taskQue;


    public ThreadExecutor(int workThreadSize, int trheadQueSize) {
        this.taskQue = new LinkedBlockingQueue<>(trheadQueSize);
        this.workThreadList = new ArrayList<WorkThread>();
        for (int i = 0; i < workThreadSize; i++) {
            WorkThread workThread = new WorkThread();
            workThread.start();
            this.workThreadList.add(workThread);

        }
    }

    public boolean excute(Runnable thread) {
        boolean offer = taskQue.offer(thread);
        return  offer;
    }

    class  WorkThread extends Thread {
        @Override
        public void run() {
            while(true) {
                Runnable poll = taskQue.poll();
                if(poll != null) {
                    poll.run();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadExecutor threadExecutor = new ThreadExecutor(4, 10);
        for (int i =0;i < 20; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("你好哇：" + Thread.currentThread().getName());
                }
            };
            threadExecutor.excute(runnable);
        }

    }
}

