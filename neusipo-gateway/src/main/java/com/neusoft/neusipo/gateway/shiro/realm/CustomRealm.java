package com.neusoft.neusipo.gateway.shiro.realm;

import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.core.util.JWTUtil;
import com.neusoft.neusipo.gateway.feign.AuthClient;
import com.neusoft.neusipo.gateway.shiro.token.JWTToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: shiro用户认证、授权中心
 * @author: zhengchj
 * @create: 2019-11-02 12:01
 **/
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private AuthClient authClient;

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = (UserInfo) getAvailablePrincipal(principalCollection);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set roles = new HashSet<>();
        roles.add(userInfo.getRole());
        //TODO
        info.setRoles(roles);
        info.setStringPermissions(new HashSet<>());
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtil.getUsername(token);
        if(username == null){
            throw new AuthenticationException("token invalid");
        }
        Response<UserInfo> response = authClient.loadByUsername(username);
        if(response.getStatus() != 200){
            throw new AuthenticationException("authentication failure");
        }
        if(!JWTUtil.verify(token, username, JWTUtil.JWT_SECRET)){
            throw new AuthenticationException("token expired");
        }
        return new SimpleAuthenticationInfo(response.getData(), token, getName());
    }
}
