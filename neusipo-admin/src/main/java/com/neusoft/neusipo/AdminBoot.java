package com.neusoft.neusipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: admin服务启动类
 * @author: zhengchj
 * @create: 2019-11-01 09:41
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
public class AdminBoot {
    public static void main(String[] args) {
        SpringApplication.run(AdminBoot.class, args);
    }
}
