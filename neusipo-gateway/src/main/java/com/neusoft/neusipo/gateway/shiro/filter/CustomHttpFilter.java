package com.neusoft.neusipo.gateway.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.context.RequestContext;
import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.gateway.Constant;
import com.neusoft.neusipo.gateway.shiro.token.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 用户认证过滤器
 * @author: zhengchj
 * @create: 2019-11-02 12:07
 **/
@Slf4j
public class CustomHttpFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(Constant.TOKEN_KEY);

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try{
            getSubject(request, response).login(token);
        }catch (Exception e){
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response) && executeLogin(request, response)) {
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.addZuulRequestHeader(Constant.USER_ID_KEY, userInfo.getId());
            return true;
        }else{
            write403(request, response);
            return false;
        }
    }
    //直接返回false，避免当访问拒绝时页面弹出登录窗口
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(Constant.TOKEN_KEY);
        return authorization != null;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        log.info("servletpath:{}", httpServletRequest.getServletPath());
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    private void write403(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            Response<String> response = new Response<>();
            response.setStatus(403);
            httpServletResponse.setStatus(403);
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(response));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
