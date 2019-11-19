package com.neusoft.neusipo.core.message.rocketmq;

import com.neusoft.neusipo.core.message.AbstractDefaultConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;

/**
 * @description: rockemq消费者基类
 * @author: zhengchj
 * @create: 2019-09-11 10:26
 **/
@Slf4j
public abstract class AbstractMQConsumer extends AbstractDefaultConsumer {
    private DefaultMQPushConsumer consumer;
    @Value("${rocketmq.namesrv}")
    private String namesrv;
    @Value("${rocketmq.consumer.group}")
    private String group;
    private String topic;
    @Override
    public void config() {
        consumer = new DefaultMQPushConsumer(group, false);
        consumer.setNamesrvAddr(namesrv);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void listen() {
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            Message msg = list.get(0);
            String topic = msg.getTopic();
            String body;
            try {
                body = new String(msg.getBody(), "utf-8");
                consume(topic, body);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        try {
            consumer.start();
            log.info("rocketmq消费者已经启动");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    @Override
    @PreDestroy
    public void destroy() {
        if(consumer != null){
            consumer.shutdown();
            log.info("rocketmq消费者已经关闭");
        }
    }
    public abstract void consume(String topic, String message);

    public void setNamesrv(String namesrv) {
        this.namesrv = namesrv;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
