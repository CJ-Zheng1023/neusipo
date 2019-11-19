package com.neusoft.neusipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description: 网关启动类
 * @author: zhengchj
 * @create: 2019-10-30 17:54
 **/
@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
@EnableCaching
public class GatewayBoot {
    public static void main(String[] args) {
        SpringApplication.run(GatewayBoot.class, args);
    }
}
