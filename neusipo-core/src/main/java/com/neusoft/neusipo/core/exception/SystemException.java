package com.neusoft.neusipo.core.exception;

import java.io.InputStream;
import java.util.Properties;

/**
 * @description: 系统异常类
 * @author: zhengchj
 * @create: 2019-11-01 16:01
 **/
public class SystemException extends RuntimeException {
    private String code;
    private static Properties errorProperties = new Properties();
    static {
        try {
            InputStream errorInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("error-code.properties");
            if(errorInputStream != null){
                errorProperties.load(errorInputStream);
                errorInputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public SystemException(){
        super();
    }
    public SystemException(String code){
        super();
        this.code =code;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return errorProperties.getProperty(code, "");
    }
    public String getCode(){
        return this.code;
    }
}
