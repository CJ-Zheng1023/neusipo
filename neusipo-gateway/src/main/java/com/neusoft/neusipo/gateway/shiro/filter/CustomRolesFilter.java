package com.neusoft.neusipo.gateway.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.neusoft.neusipo.core.base.Response;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @description: 重写onAccessDenied方法，解决默认的shiro角色验证过滤器在post请求认证不通过时返回空response的问题
 * @author: zhengchj
 * @create: 2019-11-02 12:12
 **/
public class CustomRolesFilter extends RolesAuthorizationFilter {

    /**
     * 重写授权方法，当满足一个角色要求时授权通过
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if(rolesArray == null || rolesArray.length == 0){
            return true;
        }
        for(String role : rolesArray){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        Response<String> res = new Response<>();
        response.setContentType("application/json;charset=utf-8");
        if (subject.getPrincipal() == null) {
            res.setStatus(401);
        } else {
            res.setStatus(403);
        }
        response.getWriter().write(JSON.toJSONString(res));
        WebUtils.toHttp(response).setStatus(403);
        return false;
    }
}
