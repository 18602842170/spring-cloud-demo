package com.cloud.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 配置为controller
@RequestMapping(value = "/test")
public class testcon {
    
    @Value("${server.port}") // 读取配置文件的端口
    String port;
    
    /**
     * 设置一个方法
     * 参数什么的 可以用HttpServletRequest  也可以用 @RequestParam
     * 测试我就不添加了
     * @return
     */
    @RequestMapping(value = "/test")
    public String testMethod() {
        return "Hello World!!! 端口为:" + port;
    }
}
