
package com.cloud.client;

import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.sentry.Sentry;

@SpringBootApplication
@EnableEurekaClient // 配置客户端
public class SpringCloudClient2Application {
    
    public static void main(String[] args) throws MQClientException {
        SpringApplication.run(SpringCloudClient2Application.class, args);
        Sentry.init();
    }
    
}
