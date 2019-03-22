package com.lzhiy.serviceribbin;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiserviceError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://eurka-client1/hi?name="+name + "------ribbin",String.class);
    }

    public String hiserviceError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
