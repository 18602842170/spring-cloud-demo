package com.cloud.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 配置为controller
@RequestMapping(value = "/aop")
public class AopTestCon {
    
    @RequestMapping("/hello")
    public Object hello() {
        return new World("aop", "1");
    }
    
    class World {
        public String name;
        public String id;
        
        public World(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }
}
