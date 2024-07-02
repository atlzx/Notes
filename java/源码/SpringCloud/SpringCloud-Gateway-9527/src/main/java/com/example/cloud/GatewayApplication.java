package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.ZonedDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
//        getTime();
        SpringApplication.run(GatewayApplication.class,args);
    }

    public static void getTime(){
        // 通过ZonedDateTime的now方法可以得到当前的时间
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
