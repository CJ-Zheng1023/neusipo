package com.neusoft.neusipo.gateway.message.rocketmq;


import com.neusoft.neusipo.core.message.rocketmq.AbstractMQConsumer;
import com.neusoft.neusipo.gateway.service.AuthService;
import com.neusoft.neusipo.gateway.shiro.factory.ShiroFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description: rocketmq权限更新消费者
 * @author: zhengchj
 * @create: 2019-09-02 13:13
 **/
@Slf4j
/*@Component*/
public class UpdateFilterMapConsumer extends AbstractMQConsumer {
    @Autowired
    AuthService authService;
    @Autowired(required = false)
    ShiroFilterFactory factory;
    @Value("${topic.update.filter.map}")
    private String topic;

    public void init(){
        this.setTopic(topic);
        super.init();
    }

    @Override
    public void consume(String topic, String message) {
        log.info("接收消息---topic[{}],message[{}]", topic, message);
        authService.updateFilterChainDefinitions(factory);
    }
}
