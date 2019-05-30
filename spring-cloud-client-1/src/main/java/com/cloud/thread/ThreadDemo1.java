package com.cloud.thread;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/thread_demo1")
public class ThreadDemo1 extends Thread {
    
    public ThreadDemo1(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        while (!interrupted()) {
            System.out.println("线程" + Thread.currentThread().getName() + "执行了。。。。");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
