package com.cloud.thread;

/**
 * Runnable接口中只有一个run方法，
 * 实现Runnable接口的run方法作为任务处理方法，
 * 将Runnable的实现类对象传入到Thread的构造中，
 * 创建线程，并使用Thread.start()启动线程。
 * @author Administrator
 *
 */
public class ThreadDemo2 implements Runnable {
    /**
    * 重写run方法
    */
    @Override
    public void run() {
        System.out.println("执行了");
    }
}
