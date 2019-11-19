package com.neusoft.neusipo.core.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.neusipo.core.base.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Optional;

/**
 * @description: 控制器返回数据增强，作用：拦截控制器，获取返回数据，封装成Response对象
 * @author: zhengchj
 * @create: 2019-11-01 11:30
 **/
@ControllerAdvice
@Slf4j
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof Response){
            return o;
        } else if(o instanceof Optional){
            Optional opt = (Optional) o;
            Response<Object> response = new Response<>();
            if(opt.isPresent()){
                response.setData(opt.get());
            }else{
                response.setData(new HashMap());
            }
            response.setStatus(200);
            return response;
        } else if(o instanceof String){
            Response<Object> response = new Response();
            response.setData(o);
            response.setStatus(200);
            ObjectMapper mapper = new ObjectMapper();
            String result = "";
            try {
                result = mapper.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return result;
        }else{
            Response<Object> response = new Response<>();
            if(o == null){
                response.setData(new HashMap());
            }else{
                response.setData(o);
            }
            response.setStatus(200);
            return response;
        }
    }
}
