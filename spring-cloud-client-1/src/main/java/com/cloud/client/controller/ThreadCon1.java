package com.cloud.client.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.client.service.AsyncServiceDemo1;
import com.cloud.thread.ThreadDemo1;
import com.cloud.thread.ThreadDemo2;
import com.cloud.thread.ThreadDemo3;

@RestController
@RequestMapping(value = "/thread_demo1")
public class ThreadCon1 {
    
    @Autowired
    AsyncServiceDemo1 asyncServiceDemo1;
    
    /**
     * 多线程可以通过继承Thread类并重新Thread的run方法来启动多线程。然后通过Thread的start方法来启动线程。
     * @return
     */
    @RequestMapping(value = "/test1")
    public Object ThreadTest1() {
        ThreadDemo1 demo1 = new ThreadDemo1("one");
        ThreadDemo1 demo2 = new ThreadDemo1("two");
        demo1.start();
        demo2.start();
        /**
         * 这里也顺便用了线程的中断，当希望一个线程不再执行时，
         * 就是用interrupt()方法来进行中断，此时的的线程对象将不会再执行，interrupted()方法判断线程是否中断，返回boolean值，
         * 当当前线程被中断时返回false，使用interrupted()方法可以避免报InterruptedException异常。
         */
        demo1.interrupt();
        demo1.interrupted();
        
        return "test1 over";
    }
    
    /**
     * 这里的Runnable接口其实是作为一个线程任务处理器，
     * 查看Thread中的run方法和构造方法可以看出，
     * 但传入的target(Runnable对象)不为空时，
     * 执行Runnable的run方法，
     * 所以通过启动线程时实际先执行的还是Thread中的run方法去调用target的run方法。
     * @return
     */
    @RequestMapping(value = "/test2")
    public Object ThreadTest2() {
        
        ThreadDemo2 demo2 = new ThreadDemo2();
        //将demo2传入到Thread中
        Thread thread = new Thread(demo2);
        Thread thread1 = new Thread(demo2);
        
        thread.start();
        thread1.start();
        
        return "test1 over";
    }
    
    /**
     * 匿名内部类其实跟继承Thread类并重写run方法原理一样，都是通过覆盖父类run方法，
     * 执行当前对象的run方法的方式来执行只需要处理的任务，只是写法上跟简洁，并且只会执行一次，
     * 如果任务只需要执行一次时并且减少代码量时可以使用
     * @return
     */
    @RequestMapping(value = "/test3")
    public Object ThreadTest3() {
        
        //匿名内部类启动多线程
        new Thread() {
            @Override
            public void run() {
                System.out.println("Thread in running");
            }
        }.start();
        
        return "test1 over";
    }
    
    /**
     * 还可以通过Runnable的匿名内部类来实现
     * @return
     */
    @RequestMapping(value = "/test4")
    public Object ThreadTest4() {
        
        //匿名内部类启动多线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable thread is running");
            }
        }).start();
        
        return "test1 over";
    }
    
    /**
     * 在这里需要注意的时，当同时使用Thread的匿名内部类和Runnable接口的匿名内部类同时使用时，
     * 此时执行的是Thread的run方法，而不会执行Runnable的run方法，
     * 原因是根据源码可以看出此时run方法已经被重写，所以不会调用target.run语句，所以Runnable的run方法不会执行。
     * @return
     */
    @RequestMapping(value = "/test5")
    public Object ThreadTest5() {
        
        //Thread匿名内部类和Runnable匿名内部类同时存在时，打印sub is running
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable");
            }
        }) {
            @Override
            public void run() {
                System.out.println("sub is running");
            }
        }.start();
        
        return "test1 over";
    }
    
    /**
     * 在这里需要注意的时，当同时使用Thread的匿名内部类和Runnable接口的匿名内部类同时使用时，
     * 此时执行的是Thread的run方法，而不会执行Runnable的run方法，
     * 原因是根据源码可以看出此时run方法已经被重写，所以不会调用target.run语句，所以Runnable的run方法不会执行。
     * task.get()将会等待线程的返回值。
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/test6")
    public Object ThreadTest6() throws InterruptedException, ExecutionException {
        
        ThreadDemo3 taskDemo = new ThreadDemo3();
        
        //通过demo4创建task对象
        FutureTask<Integer> task = new FutureTask<>(taskDemo);
        
        //通过task创建并启动线程
        Thread thread = new Thread(task);
        thread.start();
        Integer result = task.get();
        Integer a22 = 2;
        
        return "计算结果为：" + result;
    }
    
    /**
     * 由于线程的创建和销毁都会消耗过多的内存资源，所以在jdk5之后JAVA新增了线程池的概念，
     * ThreadPoolExecutor是线程池的核心类，它重载了很多的构造方法让我们构造一个线程池，
     * 在线程池中创建指定多个的线程，执行任务时，将从线程池中取出线程去执行任务，任务执行完成后将线程归还到线程池中。
     * 线程池不用频繁的创建和销毁线程池，减少了资源的消耗，提高了性能，
     * 可以直接通过new ThreadPoolExecutor()来指定自己创建线程池，
     * 也可以使用Executors中的静态方法来创建各种类型的线程池，这里先创建一个制定大小的线程池，
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/test7")
    public Object ThreadTest7() throws InterruptedException, ExecutionException {
        //创建线程池大小为10的线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //循环执行100次，打印线程名字，发现总是只用10个线程在执行
        for (int i = 0; i < 100; i++) {
            /**
             * executor.execute()传入Runnable对象，执行任务，
             * 这里使用lambda表达式写法，其实就是一个Runnable匿名对象，打印当前线程名称。
             * 当任务100次循环之后我们发现程序并没有结束，这是因为线程池仍然存活，此时调用executor.shutdown();方法关闭线程池，结束程序。
             * 因为Executors中的静态方法也是通过ThreadPoolExecutor来创建的，所以这里就不写直接创建的案例了，
             */
            executor.execute(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        
        return "test1 over";
    }
    
    /**
     * 启动springboot，调用DemoService的a/b方法，可以看到他们异步执行。
     * 这里只是为了方便引入spring的依赖，直接使用spring也是可以实现异步方法调用的spring也支持计划任务，使用@EnableScheduling开启计划任务，
     * @Scheduled标记计划任务的方法，在注解中传入相应的执行时间和周期即可实现计划任务
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/test8")
    public Object ThreadTest8() {
        asyncServiceDemo1.a();
        asyncServiceDemo1.b();
        
        return "test1 over";
    }
    
    /**
     * 在java.util包中提供了一个Timer类可以用来创建定时任务，
     * 可以指定某个时间开始执行，
     * 之后每隔多长时间执行一次。
     * Timer接收一个TimerTask为要执行的任务，其余参数为需要执行的时间方式，可以进入Timer的源码查看个参数的意义，构建自己想要的计划任务。
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value = "/test9")
    public Object ThreadTest9() {
        Timer timer = new Timer();
        //  创建1秒后开始中，没隔1秒执行一次的定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task is running");
            }
        }, 1000, 1000);
        
        return "test1 over";
    }
    
}
