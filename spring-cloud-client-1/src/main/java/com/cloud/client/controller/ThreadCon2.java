package com.cloud.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/thread_demo2")
public class ThreadCon2 {
    
    class Sequence {
        
        private int value;
        
        public int getNext() {
            return value++;
        }
        
    }
    
    /**
     * 多线程可以通过继承Thread类并重新Thread的run方法来启动多线程。然后通过Thread的start方法来启动线程。
     * @return
     */
    @RequestMapping(value = "/test1")
    public Object ThreadTest1() {
        Sequence sequence = new Sequence();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                }
            }
        }).start();
        
        return "test1 over";
    }
    
}
