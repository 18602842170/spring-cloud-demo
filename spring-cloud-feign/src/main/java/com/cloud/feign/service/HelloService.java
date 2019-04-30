package com.cloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloud.feign.component.HelloComponent;

/**
 * fallback 中使用 HelloComponent.class
 * 当接口sayHello()失败时，会返回HelloComponent中的sayHello()实现
 * @author Administrator
 *
 */
@FeignClient(value = "client1", fallback = HelloComponent.class)
public interface HelloService {
    
    // value值必须与client中路径一致
    @RequestMapping(value = "/test/test", method = RequestMethod.GET)
    String sayHello();
    
}
