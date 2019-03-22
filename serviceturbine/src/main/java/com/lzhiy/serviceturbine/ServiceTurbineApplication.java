package com.lzhiy.serviceturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableTurbine //支持断路器聚合集群
public class ServiceTurbineApplication {
    /**
     *
     * 当前集群端口是8764
     *
     * 先启动eurekaserver，再启动两个eurekaclient，最后启动service-turbine
     * 断路器集群数据访问地址 http://localhost:8764/turbine.stream
     *
     * 在其中一个eurekaclient的port下打开Hystrix DashBoard
     *
     * http://localhost:${任意一个client的port}/hystrix,
     * 输入监控流http://localhost:${当前断路器集群端口}/turbine.stream
     * 即可看集群中各个client的数据DashBoard
     */

    public static void main(String[] args) {
        SpringApplication.run( ServiceTurbineApplication.class, args );
    }
}
