package com.neusoft.neusipo.core.aspect;

import com.neusoft.neusipo.core.annotation.Execution;
import com.neusoft.neusipo.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @description: 拦截@Excution注解的切面
 * @author: zhengchj
 * @create: 2019-11-01 15:09
 **/
@Aspect
@Component
@Slf4j
public class ExecutionAspect {
    private static Properties logProperties = new Properties();

    static {
        try {
            InputStream logInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("log-code.properties");
            if(logInputStream != null){
                logProperties.load(logInputStream);
                logInputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Pointcut("@annotation(com.neusoft.neusipo.core.annotation.Execution)")
    public void pointCut() {
    }
    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        Execution execution = method.getAnnotation(Execution.class);
        String logCode = execution.logCode();
        String errorCode = execution.errorCode();
        try{
            Object obj = pjp.proceed();
            String logMsg = logProperties.getProperty(logCode, "");
            log.info("业务日志：[{}]", logMsg);
            return obj;
        }catch (Throwable t){
            t.printStackTrace();
            if(t instanceof SystemException){
                SystemException e = (SystemException) t;
                throw new SystemException(e.getCode());
            }else{
                throw new SystemException(errorCode);
            }
        }
    }
}
