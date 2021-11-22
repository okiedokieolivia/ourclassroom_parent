package com.ourclassroom.ourclassroom.service.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//since mysql is added as a dependency in its parent module `service`
//have to exclude data source configuration otherwise should add the mysql config to this module
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.ourclassroom.ourclassroom"})
@EnableDiscoveryClient
public class ServiceOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceOssApplication.class, args);
    }

}