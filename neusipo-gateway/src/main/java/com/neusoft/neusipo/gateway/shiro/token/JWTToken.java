package com.neusoft.neusipo.gateway.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description: shiro token实现
 * @author: zhengchj
 * @create: 2019-11-01 18:48
 **/
public class JWTToken implements AuthenticationToken {
    private String token;
    public JWTToken(String token){
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
