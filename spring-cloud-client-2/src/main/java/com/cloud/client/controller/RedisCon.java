package com.cloud.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.client.Entity.User;
import com.cloud.client.service.RedisService;

@RestController // 配置为controller
@RequestMapping(value = "/redis")
public class RedisCon {
    
    @Autowired
    RedisService<User, String> redisService;
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "/list")
    public Object testRidesList() {
        List<User> users;
        
        String key = "users";
        
        // 缓存存在
        users = redisService.listFindAll(key);
        if (users == null) {
            users = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                User user = new User();
                user.name = "测试";
                user.age = 188 + i;
                user.createTime = new Date();
                redisService.listPush(key, user);
                users.add(user);
            }
            // 插入缓存
        }
        
        return users;
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value = "/object")
    public Object testRides() {
        User user;
        
        String key = "user";
        
        // 缓存存在
        user = redisService.getValue(key);
        if (user == null) {
            user = new User();
            user.name = "测试";
            user.age = 188;
            user.createTime = new Date();
            // 插入缓存
            redisService.setValue(key, user, 60);
        }
        
        return user;
    }
    
}
