package com.neusoft.neusipo.gateway.shiro.factory;

import com.neusoft.neusipo.gateway.service.AuthService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: shiro过滤器工厂
 * @author: zhengchj
 * @create: 2019-11-02 12:21
 **/
public class ShiroFilterFactory extends ShiroFilterFactoryBean {
    @Value("${shiro.enabled}")
    private boolean shiroEnabled;
    @Autowired
    private AuthService authService;
    @PostConstruct
    public void load(){
        // 当关闭用户验证开关，所有请求无权限通过
        if(!shiroEnabled){
            Map<String, String> filterRuleMap = new HashMap<>();
            filterRuleMap.put("/**", "anon");
            super.setFilterChainDefinitionMap(filterRuleMap);
        }else{
            this.loadFilterChainDefinitions();
        }
    }
    private void loadFilterChainDefinitions(){
        Map<String, String> map = authService.loadFilterChainDefinitions();
        super.setFilterChainDefinitionMap(map);
    }
}
