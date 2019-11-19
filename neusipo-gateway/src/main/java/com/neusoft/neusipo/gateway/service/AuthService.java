package com.neusoft.neusipo.gateway.service;

import com.neusoft.neusipo.gateway.shiro.factory.ShiroFilterFactory;

import java.util.Map;

/**
 * @description: 权限获取、更新接口
 * @author: zhengchj
 * @create: 2019-11-02 12:29
 **/
public interface AuthService {
    Map<String, String> loadFilterChainDefinitions();
    void updateFilterChainDefinitions(ShiroFilterFactory factory);
}
