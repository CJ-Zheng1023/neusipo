package com.neusoft.neusipo.core.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @description: 状态码工具类
 * @author: zhengchj
 * @create: 2019-10-30 17:39
 **/
public class StatusCodeUtil {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("status-code.properties");
            properties.load(inputStream);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getMessage(int code){
        return properties.getProperty(code+"", "");
    }
}
