package com.neusoft.neusipo.gateway.config;

import com.neusoft.neusipo.gateway.Constant;
import com.neusoft.neusipo.gateway.shiro.factory.ShiroFilterFactory;
import com.neusoft.neusipo.gateway.shiro.filter.CustomHttpFilter;
import com.neusoft.neusipo.gateway.shiro.filter.CustomRolesFilter;
import com.neusoft.neusipo.gateway.shiro.realm.CustomRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: shiro配置
 * @author: zhengchj
 * @create: 2019-11-02 12:23
 **/
@Configuration
public class ShiroConfig {

    @Bean("realm")
    public CustomRealm getRealm(){
        return new CustomRealm();
    }
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(CustomRealm realm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        /**
         * 关闭shiro自带的session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }
    @Bean
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean factory = new ShiroFilterFactory();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(Constant.SHIRO_HTTP_FILTER_NAME, new CustomHttpFilter());
        filterMap.put(Constant.SHIRO_ROLES_FILTER_NAME, new CustomRolesFilter());
        factory.setFilters(filterMap);
        factory.setSecurityManager(securityManager);
        //factory.setUnauthorizedUrl("/403");
        return factory;
    }
}
