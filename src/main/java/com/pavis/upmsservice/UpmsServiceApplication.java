package com.pavis.upmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableEurekaClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("com.pavis.upmsservice.mapper")
public class UpmsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsServiceApplication.class, args);
    }

}

