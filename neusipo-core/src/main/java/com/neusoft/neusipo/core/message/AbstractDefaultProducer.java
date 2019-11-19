package com.neusoft.neusipo.core.message;


import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: zhengchj
 * @create: 2019-11-02 09:48
 **/
public abstract class AbstractDefaultProducer implements DefaultProducer {

    @Override
    @PostConstruct
    public void init() {
        config();
    }
    public abstract void config();
}
