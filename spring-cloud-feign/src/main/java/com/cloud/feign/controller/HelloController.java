package com.cloud.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.feign.service.HelloService;

@RestController
public class HelloController {
    
    @Autowired
    HelloService helloService;
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET) // 随便起个自己喜欢的访问名字
    public String hi(@RequestParam String name) {
        return helloService.sayHello();
    }
    
}
