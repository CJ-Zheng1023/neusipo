package com.neusoft.neusipo.core.base;

import com.neusoft.neusipo.core.util.StatusCodeUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @description: 响应对象
 * @author: zhengchj
 * @create: 2019-10-30 17:19
 **/
@Data
public class Response<T> implements Serializable {
    public Response(){}
    public Response(int status){
        this.status = status;
    }
    private int status;
    private T data;
    private String message;
    public String getMessage(){
        if(StringUtils.isBlank(this.message)){
            this.message = StatusCodeUtil.getMessage(this.status);
        }
        return this.message;
    }
}
