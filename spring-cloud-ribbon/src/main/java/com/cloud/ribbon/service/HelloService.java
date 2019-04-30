package com.cloud.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 问题一: 什么是REST REST（RepresentationalState Transfer）是Roy Fielding
 * 提出的一个描述互联系统架构风格的名词。REST定义了一组体系架构原则，您可以根据这些原则设计以系统资源为中心的Web
 * 服务，包括使用不同语言编写的客户端如何通过 HTTP处理和传输资源状态。
 * 
 * 为什么称为 REST？Web本质上由各种各样的资源组成，资源由URI
 * 唯一标识。浏览器（或者任何其它类似于浏览器的应用程序）将展示出该资源的一种表现方式，或者一种表现状态。
 * 如果用户在该页面中定向到指向其它资源的链接，则将访问该资源，并表现出它的状态。
 * 这意味着客户端应用程序随着每个资源表现状态的不同而发生状态转移，也即所谓REST。
 * 
 * 问题二: RestTemplate Spring'scentral class for synchronous client-side HTTP
 * access.It simplifies communication with HTTPservers, and enforces RESTful
 * principles. Ithandles HTTP connections, leaving application code to provide
 * URLs(with possible template variables) andextract results.
 * 简单说就是：简化了发起HTTP请求以及处理响应的过程，并且支持REST
 * 
 * @author lbr
 *
 * 2018年11月28日 上午11:10:45
 */

@Service
public class HelloService {
    
    @Autowired
    RestTemplate restTemplate; //
    
    public String hiService(String name) {
        return restTemplate.getForObject("http://client1/test/test", String.class);
    }
    
}
