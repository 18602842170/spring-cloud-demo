package com.cloud.feign.component;

import org.springframework.stereotype.Component;

import com.cloud.feign.service.HelloService;

/**
 * 创建HelloComponent类(HelloComponent需要实现HelloService 接口，并注入到Ioc容器中)
 * @author Administrator
 *
 */
@Component
public class HelloComponent implements HelloService {
    
    @Override
    public String sayHello() {
        return "sorry";
    }
}
