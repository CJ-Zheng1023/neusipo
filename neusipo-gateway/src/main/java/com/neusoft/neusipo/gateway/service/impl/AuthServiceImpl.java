package com.neusoft.neusipo.gateway.service.impl;

import com.neusoft.neusipo.common.vo.RoleInfo;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.gateway.Constant;
import com.neusoft.neusipo.gateway.feign.AuthClient;
import com.neusoft.neusipo.gateway.service.AuthService;
import com.neusoft.neusipo.gateway.shiro.factory.ShiroFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 权限获取、更新接口实现
 * @author: zhengchj
 * @create: 2019-11-02 12:30
 **/
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthClient authClient;
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> map = new HashMap<>();
        map.put("/api/admin/user/login", "anon");
        map.put("/403", "anon");
        map.put("/info", "anon");
        map.put("/error", "anon");
        Response<List<RoleInfo>> response = authClient.loadRoles();
        if(response.getStatus() == 200){
            List<RoleInfo> roleInfos = response.getData();
            for(RoleInfo roleInfo : roleInfos){
                List<String> patterns = roleInfo.getPatterns();
                String roleName = roleInfo.getName();
                for(String pattern : patterns){
                    String roleStr = map.get(pattern);
                    //同个url赋予多权限
                    if(roleStr != null){
                        roleStr = roleStr.replace("roles[", "roles[" + roleName + ",");
                        map.put(pattern, roleStr);
                    }else{
                        map.put(pattern, Constant.SHIRO_HTTP_FILTER_NAME + ",roles[" + roleName + "]");
                    }
                }
            }
        }
        map.put("/**", Constant.SHIRO_HTTP_FILTER_NAME);
        return map;
    }
    @Override
    public void updateFilterChainDefinitions(ShiroFilterFactory factory){
        synchronized (this){
            AbstractShiroFilter filter;
            try {
                filter = (AbstractShiroFilter) factory.getObject();
            } catch (Exception e) {
                log.error("get ShiroFilter error!!!!!!!!!");
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) filter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            log.info("开始清空权限......");
            manager.getFilterChains().clear();
            factory.getFilterChainDefinitionMap().clear();
            log.info("开始重新加载权限......");
            factory.load();
            log.info("开始重新构建权限......");
            Map<String, String> chains = factory.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
            log.info("权限重建完毕!");
        }
    }
}
