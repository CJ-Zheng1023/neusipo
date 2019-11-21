package com.neusoft.neusipo.admin.controller;

import com.neusoft.neusipo.core.annotation.SendMessage;
import com.neusoft.neusipo.core.message.DefaultProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 消息中间件测试
 * @author: zhengchj
 * @create: 2019-11-21 16:15
 **/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private DefaultProducer producer;
    @Value("${topic.update.filter.map}")
    private String topic;
    @GetMapping("/test1")
    public void test1(){
        producer.asyncSend(topic, "这是一条注入发送的消息");
    }
    @GetMapping("/test2")
    @SendMessage(topic = "topic1", message = "这是一条注解发送的消息")
    public void test2(){
    }
}
