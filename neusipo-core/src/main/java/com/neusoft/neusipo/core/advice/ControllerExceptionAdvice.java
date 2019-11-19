package com.neusoft.neusipo.core.advice;

import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: controller全局异常处理
 * @author: zhengchj
 * @create: 2019-11-01 12:39
 **/
@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    public Response<String> globalErrorHandler(Exception e){
        log.error("Global Exception:", e);
        return new Response<>(500);
    }
    @ExceptionHandler(value = SystemException.class)
    public Response<String> systemErrorHandler(SystemException e){
        Response response = new Response();
        response.setStatus(500);
        response.setMessage(e.getMessage());
        return response;
    }
}
