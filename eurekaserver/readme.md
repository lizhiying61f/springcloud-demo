高可用服务注册中心

当成千上万个服务注册在Eureka Server时，它的负载非常高，这在生产环境不合适，
所以通过Eureka运行多个实例，使其更具有可靠性。
即使其中一个宕机了，另外的实例也能保证注册中心的正常运行
resources下创建两个配置文件:
application-demo1.yml
application-demo2.yml

在系统host下添加对应的过滤
127.0.0.1 demo1
127.0.0.1 demo2
同时client也要修改默认指向localhost的地址为demo的地址

java -jar eurekaserver-0.0.1-SNAPSHOT.jar --spring.profiles.active=demo1
java -jar eurekaserver-0.0.1-SNAPSHOT.jar --spring.profiles.active=demo2