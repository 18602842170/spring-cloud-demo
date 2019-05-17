package com.cloud.client.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.client.Entity.User;
import com.cloud.client.common.SentryUtil;
import com.cloud.client.imp.TransactionListenerImpl;
import com.cloud.client.service.RedisService;

@RestController // 配置为controller
@RequestMapping(value = "/test")
public class testcon {
    
    @Value("${server.port}") // 读取配置文件的端口
    String port;
    
    @Autowired
    RedisService<User, String> redisService;
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "/redis")
    public Object testRides() {
        User user;
        
        String key = "user";
        
        // 缓存存在
        user = redisService.getValue(key);
        if (user == null) {
            user = new User();
            user.name = "测试";
            user.age = 188;
            user.createTime = new Date();
            // 插入缓存
            redisService.setValue(key, user, 60);
        }
        
        return user;
    }
    
    /**
     * 设置一个方法
     * 参数什么的 可以用HttpServletRequest  也可以用 @RequestParam
     * @return
     * @throws MQClientException 
     * @throws InterruptedException 
     * @throws UnsupportedEncodingException 
     * @throws MQBrokerException 
     * @throws RemotingException 
     */
    @RequestMapping(value = "/test")
    public String testMethod() throws Exception {
        
        //初始化TransactionListenerImpl
        TransactionListenerImpl transactionListener = new TransactionListenerImpl();
        
        //创建事物transactionProducer
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        //由于本地回调监听跟消息的发送会并发进行，所以可以使用线程池来执行操作
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        
        producer.setNamesrvAddr("192.168.56.11105:9876;192.168.56.11107:9876");
        //设置线程池
        producer.setExecutorService(executorService);
        //设置事物监听
        producer.setTransactionListener(transactionListener);
        
        producer.start();
        
        String[] tags = new String[] { "TagA", "TagB", "TagC", "TagD", "TagE" };
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message("TopicTest1234", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                // 使用sentry抛出异常
                Map<String, Object> tag = new HashMap();
                tag.put("index", i);
                tag.put("message", "Hello RocketMQ " + i);
                tag.put("Topic", "TopicTest1234");
                tag.put("tag", tags[i % tags.length]);
                tag.put("key", "KEY" + i);
                SentryUtil.sendErrorMessage(e, new String[] { "发送信息时出现异常", "文件：testcon", "方法：testMethod" }, tag, null);
            } finally {
                producer.shutdown();
            }
        }
        return "Hello World!!! 端口为:" + port;
    }
}
