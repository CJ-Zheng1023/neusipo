package com.neusoft.neusipo.core.message;

/**
 * @description: 消息生产者接口
 * @author: zhengchj
 * @create: 2019-11-02 09:33
 **/
public interface DefaultProducer {
    void init();
    void asyncSend(String topic, String message);
    void destroy();
}
