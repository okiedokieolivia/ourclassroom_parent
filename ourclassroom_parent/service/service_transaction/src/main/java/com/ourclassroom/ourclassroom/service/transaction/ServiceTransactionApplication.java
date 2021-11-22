package com.ourclassroom.ourclassroom.service.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ourclassroom.ourclassroom"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceTransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTransactionApplication.class, args);
    }

}
