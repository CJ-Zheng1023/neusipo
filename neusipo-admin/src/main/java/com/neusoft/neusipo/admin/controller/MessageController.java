package com.neusoft.neusipo.admin.controller;

import com.neusoft.neusipo.core.annotation.SendMessage;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.core.message.DefaultProducer;
import com.neusoft.neusipo.core.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 消息中间件测试
 * @author: zhengchj
 * @create: 2019-11-21 16:15
 **/
/*@RestController
@RequestMapping("/message")*/
public class MessageController {
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private DefaultProducer producer;
    @Value("${topic.update.filter.map}")
    private String topic;
    @GetMapping("/test1")
    public void test1(){
        producer.asyncSend(topic, "这是一条注入发送的消息");
    }
    @GetMapping("/test2")
    @SendMessage(topic = "${topic.update.filter.map}", message = "这是一条注解发送的消息")
    /*@SendMessage(topic = "neusipo-update-filter", message = "这是一条注解发送的消息")*/
    public void test2(){
    }
    @GetMapping("/test3")
    public Response test3(){
        String key = "key123";
        redisRepository.set(key, "ccc");
        Response response = new Response();
        response.setStatus(200);
        response.setData(redisRepository.get(key));
        return response;
    }
}
