# spring-cloud-demo

spring cloud 的模板项目，作为集群其中包含了7个spring boot 项目

- spring-cloud 为 Eureka服务器
- spring-cloud-client 为 集群下的两个APP，APPNAME相同，以测试负载均衡
  -  其中 pring-cloud-client-1 使用 config-server 的配置中心
- spring-cloud-feign 为负载均衡APP
  - feign 中 封装 ribbon，可使用 ribbon 中的负载策略
- spring-cloud-ribbon 为负载均衡APP
- spring-cloud-zuul 为两个负载均衡节点的代理，就可以只向外部暴露一个地址。
- spring-cloud-config-server 为集群的配置文件中心。
  - 当项目多时，只需要修改spring-cloud-config-server中的git仓库中的配置文件即可

