package com.neusoft.neusipo.gateway.controller;

import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.core.redis.RedisRepository;
import com.neusoft.neusipo.gateway.feign.AuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: zuul错误处理控制器
 * @author: zhengchj
 * @create: 2019-10-30 17:56
 **/
@RestController
public class ErrorController {
    @Autowired
    private RedisRepository redisRepository;
    @GetMapping("/error")
    public Response<String> error(){
        Response<String> response = new Response<>();
        response.setStatus(502);
        return response;
    }
}
