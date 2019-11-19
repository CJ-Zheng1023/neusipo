package com.neusoft.neusipo.core.message.rocketmq;

import com.neusoft.neusipo.core.message.AbstractDefaultProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;

/**
 * @description: rocketmq生产者
 * @author: zhengchj
 * @create: 2019-09-11 09:57
 **/
@Slf4j
public abstract class AbstractMQProducer extends AbstractDefaultProducer {
    @Value("${rocketmq.namesrv}")
    private String namesrv;
    @Value("${rocketmq.producer.group}")
    private String group;
    @Value("${rocketmq.producer.timeout}")
    private int timeout;
    @Value("${rocketmq.producer.asyncSendRetry}")
    private int asyncSendRetry;
    @Value("${rocketmq.producer.sendRetry}")
    private int sendRetry;
    protected DefaultMQProducer producer;

    @Override
    public void config() {
        producer = new DefaultMQProducer(group, false);
        producer.setNamesrvAddr(namesrv);
        producer.setSendMsgTimeout(timeout);
        producer.setRetryTimesWhenSendFailed(sendRetry);
        producer.setRetryTimesWhenSendAsyncFailed(asyncSendRetry);
        try {
            producer.start();
            log.info("rocketmq生产者已经启动");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void asyncSend(String topic, String message) {
        Message msg = new Message(topic, message.getBytes());
        try {
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("topic:[{}],message:[{}]--->发送成功", topic, message);
                }
                @Override
                public void onException(Throwable throwable) {
                    log.error("topic:[{}],message:[{}]--->发送失败,异常信息:{}", topic, message, throwable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @PreDestroy
    public void destroy() {
        if(producer !=null){
            producer.shutdown();
            log.info("rocketmq生产者已关闭");
        }
    }

    public void setNamesrv(String namesrv) {
        this.namesrv = namesrv;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setAsyncSendRetry(int asyncSendRetry) {
        this.asyncSendRetry = asyncSendRetry;
    }

    public void setSendRetry(int sendRetry) {
        this.sendRetry = sendRetry;
    }
}
