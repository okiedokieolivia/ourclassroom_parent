package com.ourclassroom.ourclassroom.service.edu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan({"com.ourclassroom.ourclassroom"})
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceEduApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ServiceEduApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }
}