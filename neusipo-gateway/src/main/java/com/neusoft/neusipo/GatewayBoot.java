package com.neusoft.neusipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description: 网关启动类
 * @author: zhengchj
 * @create: 2019-10-30 17:54
 **/
@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
public class GatewayBoot {
    public static void main(String[] args) {
        SpringApplication.run(GatewayBoot.class, args);
    }
}
