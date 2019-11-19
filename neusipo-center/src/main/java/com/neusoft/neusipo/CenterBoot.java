package com.neusoft.neusipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description: 注册中心启动类
 * @author: zhengchj
 * @create: 2019-10-25 17:33
 **/
@EnableEurekaServer
@SpringBootApplication
public class CenterBoot {
    public static void main(String[] args) {
        SpringApplication.run(CenterBoot.class, args);
    }
}
