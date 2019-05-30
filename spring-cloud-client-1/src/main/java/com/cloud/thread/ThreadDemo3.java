package com.cloud.thread;

import java.util.concurrent.Callable;

public class ThreadDemo3 implements Callable<Integer> {
    
    @Override
    public Integer call() throws Exception {
        System.out.println("正在进行紧张的计算....");
        Thread.sleep(1000);
        return 1;
    }
    
}
