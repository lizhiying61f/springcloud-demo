package com.lzhiy.eurekaclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class EurekaClientApplication {

    /**
     * 断路器数据访问地址 http://localhost:8763/actuator/hystrix.stream
     * 在请求http://localhost:8763/actuator/hystrix.stream之前，需要随便请求一个接口，例如：http://localhost:8763/hi，否则会一直ping ping ping
     *
     * 断路器Dashboard访问地址 http://localhost:8763/hystrix
     * 打开断路器，在界面依次输入：http://localhost:8763/actuator/hystrix.stream 、2000 、随便；点确定。
     * 在另一个窗口输入： http://localhost:8763/hi?name=forezp
     *
     * 断路器DashBoard相关介绍：
     * 通过 Hystrix Dashboard 主页面的文字介绍，我们可以知道，Hystrix Dashboard 共支持三种不同的监控方式：
     *
     * 默认的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream 开启，实现对默认集群的监控。
     * 指定的集群监控：通过 URL：http://turbine-hostname:port/turbine.stream?cluster=[clusterName] 开启，实现对
     * clusterName 集群的监控。
     * 单体应用的监控： 通过 URL：http://hystrix-app:port/hystrix.stream 开启 ，实现对具体某个服务实例的监控。（现在这里的 URL 应该为
     * http://hystrix-app:port/actuator/hystrix.stream，Actuator 2.x 以后 endpoints
     * 全部在/actuator下，可以通过management.endpoints.web.base-path修改）
     * 前两者都对集群的监控，需要整合 Turbine 才能实现。
     *
     * DashBoard上的两个参数：
     * Delay：控制服务器上轮询监控信息的延迟时间，默认为 2000 毫秒，可以通过配置该属性来降低客户端的网络和 CPU 消耗。
     * Title：该参数可以展示合适的标题
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    public String hiError(String name){
        return "hi" + name + "error!";
    }
}
