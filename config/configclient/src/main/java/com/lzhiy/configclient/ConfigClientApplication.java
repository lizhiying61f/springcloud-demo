package com.lzhiy.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope //spring cloud bus 使用
public class ConfigClientApplication {

    /**
     * http://localhost:8881/actuator/bus-refresh
     *
     * 添加spring cloud bus 后，依次启动eureka-server、confg-cserver,启动两个config-client，端口为：8881、8882。
     * 访问http://localhost:8881/hi 或者http://localhost:8882/hi 留意浏览器显示：
     *
     * 这时我们去代码仓库将foo的值改为“foo version 4”，即改变配置文件foo的值。如果是传统的做法，需要重启服务，才能达到配置文件的更新。此时，我们只需要发送post
     * 请求：http://localhost:8881/actuator/bus-refresh，你会发现config-client会重新读取配置文件
     *
     * 另外，/actuator/bus-refresh接口可以指定服务，即使用"destination"参数，
     * 比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务。
     */

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;
    @Value("${server.port}")
    String port;
    /**
     * http://localhost:8881/hi
     * 这就说明，config-client从config-server获取了foo的属性，而config-server是从git仓库读取的
     * @return
     */
    @RequestMapping(value = "/hi")
    public String hi(){
        return foo + "-----"  + "------" + port;
    }
}
