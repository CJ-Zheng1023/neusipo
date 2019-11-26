package com.neusoft.neusipo.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.neusoft.neusipo.core.base.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @description: 熔断退回处理器
 * @author: zhengchj
 * @create: 2019-10-31 14:54
 **/
@Component
@Slf4j
public class CustomFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        // 为所有服务提供退回
        return "*";
    }
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.error(cause.getMessage());
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            return response(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }
            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }
            @Override
            public void close() {
            }
            @Override
            public InputStream getBody() throws IOException {
                Response<String> response = new Response<>();
                if(status == HttpStatus.GATEWAY_TIMEOUT){
                    response.setStatus(504);
                }else{
                    response.setStatus(503);
                }
                return new ByteArrayInputStream(JSON.toJSONString(response).getBytes());
            }
            @Override
            public HttpHeaders getHeaders() {
                // headers设定
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }
}
