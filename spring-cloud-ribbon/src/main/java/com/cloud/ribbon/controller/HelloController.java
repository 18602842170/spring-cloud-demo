package com.cloud.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.ribbon.service.HelloService;

@RestController
public class HelloController {
    
    @Autowired
    HelloService helloService;
    
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @RequestMapping(value = "/hello") // 随便起个自己喜欢的访问名字
    public String hi(@RequestParam String name) {
        //        this.loadBalancerClient.choose("com.netflix.loadbalancer.RoundRobinRule");//随机访问策略
        return helloService.hiService(name + "ribbon");
    }
    
}
