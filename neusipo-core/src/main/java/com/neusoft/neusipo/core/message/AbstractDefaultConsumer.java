package com.neusoft.neusipo.core.message;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: zhengchj
 * @create: 2019-11-02 10:22
 **/
public abstract class AbstractDefaultConsumer implements DefaultConsumer {
    @Override
    @PostConstruct
    public void init() {
        config();
        listen();
    }
    public abstract void config();
    public abstract void listen();
}
