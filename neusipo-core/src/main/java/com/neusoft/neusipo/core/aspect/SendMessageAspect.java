package com.neusoft.neusipo.core.aspect;

import com.neusoft.neusipo.core.annotation.SendMessage;
import com.neusoft.neusipo.core.message.DefaultProducer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: 截获带有@SendMessage注解的方法，等该方法成功执行后发送消息
 * @author: zhengchj
 * @create: 2019-09-11 09:38
 **/
@Component
@Aspect
@Slf4j
public class SendMessageAspect {
    @Autowired
    Environment env;

    @Autowired(required = false)
    private DefaultProducer producer;
    @Pointcut("@annotation(com.neusoft.neusipo.core.annotation.SendMessage)")
    public void pointCut() {
    }
    @AfterReturning(pointcut = "pointCut()")
    public void afterReturning(JoinPoint point){
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        SendMessage annotation = method.getAnnotation(SendMessage.class);
        String topic = env.resolvePlaceholders(annotation.topic());
        String message = annotation.message();
        producer.asyncSend(topic, message);
    }
}
