package com.cloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 配置客户端
public class SpringCloudClient2Application {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClient2Application.class, args);
    }
    
}
