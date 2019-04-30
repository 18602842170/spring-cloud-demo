package com.cloud.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudFeignApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFeignApplication.class, args);
    }
    
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();//这里配置策略
    }
    
}
