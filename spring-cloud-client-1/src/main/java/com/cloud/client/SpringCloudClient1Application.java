package com.cloud.client;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient // 配置客户端
@RestController // 配置为controller
public class SpringCloudClient1Application {
    
    public static void main(String[] args) throws MQClientException {
        SpringApplication.run(SpringCloudClient1Application.class, args);
        
        //实例化一个consumer组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group");
        //设置setNamesrvAddr，同生产者
        consumer.setNamesrvAddr("192.168.56.105:9876;192.168.56.107:9876");
        
        //设置消息读取方式，这里设置的是队尾开始读取
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        
        //设置订阅主题，第二个参数为过滤tabs的条件，可以写为tabA|tabB过滤Tab,*表示接受所有
        consumer.subscribe("TopicTest1234", "*");
        
        //注册消息监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    //的到MessageExt
                    MessageExt messageExt = list.get(0);
                    String topic = messageExt.getTopic();
                    String message = new String(messageExt.getBody(), "UTF-8");
                    int queueId = messageExt.getQueueId();
                    System.out.println("收到来自topic:" + topic + ", queueId:" + queueId + "的消息：" + message);
                    
                } catch (Exception e) {
                    //失败，请求稍后重发
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                //成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
