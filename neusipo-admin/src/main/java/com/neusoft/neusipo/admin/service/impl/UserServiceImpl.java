package com.neusoft.neusipo.admin.service.impl;

import com.neusoft.neusipo.admin.entity.Role;
import com.neusoft.neusipo.admin.entity.User;
import com.neusoft.neusipo.admin.repository.UserRepository;
import com.neusoft.neusipo.admin.service.UserService;
import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.annotation.Execution;
import com.neusoft.neusipo.core.base.BaseServiceSupport;
import com.neusoft.neusipo.core.base.Response;
import com.neusoft.neusipo.core.exception.SystemException;
import com.neusoft.neusipo.core.util.JWTUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 **/
@Service
public class UserServiceImpl extends BaseServiceSupport<User, String, UserRepository> implements UserService {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    @Execution(logCode = "10001")
    public Response<String> login(String username, String password) {
        User user = repository.findByUsername(username);
        if(user == null){
            throw new SystemException("10001");
        }else if(user.getExpireTime() != null && user.getExpireTime().before(new Timestamp(System.currentTimeMillis()))){
            throw new SystemException("10002");
        }else if(user.getLocked() == 1){
            throw new SystemException("10003");
        }else if(!user.getPassword().equals(password)){
            throw new SystemException("10001");
        }else{
            Response<String> response = new Response<>();
            response.setData(JWTUtil.sign(username, JWTUtil.JWT_SECRET, user.getExpireTime()));
            response.setStatus(200);
            return response;
        }
    }

    /**
     * 查询用户信息，用于shiro用户验证
     *
     * @param username
     * @return
     */
    @Override
    @Execution(logCode = "10002")
    public UserInfo loadByUsername(String username) {
        User user = repository.findByUsername(username);
        if(user == null){
            throw new SystemException("10001");
        }else if(user.getExpireTime() != null && user.getExpireTime().before(new Timestamp(System.currentTimeMillis()))){
            throw new SystemException("10002");
        }else if(user.getLocked() == 1){
            throw new SystemException("10003");
        }else{
            UserInfo info = new UserInfo();
            info.setId(user.getId());
            info.setUsername(user.getUsername());
            Role role = user.getRole();
            if(role != null && role.getName() != null){
                info.setRole(role.getName());
            }else{
                info.setRole("");
            }
            return info;
        }
    }
}
