package com.cloud.client.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceDemo1 {
    
    //标记方法为异步方法
    @Async
    public void a() {
        while (true) {
            System.out.println("a is running");
        }
    }
    
    @Async
    public void b() {
        while (true) {
            System.out.println("b is running");
        }
    }
    
}
