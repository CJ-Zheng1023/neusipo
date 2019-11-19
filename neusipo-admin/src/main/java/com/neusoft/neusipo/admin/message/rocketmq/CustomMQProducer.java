package com.neusoft.neusipo.admin.message.rocketmq;

import com.neusoft.neusipo.core.message.rocketmq.AbstractMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description: rocketmq消息生产者
 * @author: zhengchj
 * @create: 2019-09-02 16:30
 **/
@Slf4j
@Component
public class CustomMQProducer extends AbstractMQProducer {
}
