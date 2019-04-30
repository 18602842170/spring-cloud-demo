package com.cloud.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient // 配置客户端
@RestController // 配置为controller
public class SpringCloudClient1Application {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClient1Application.class, args);
    }
}
