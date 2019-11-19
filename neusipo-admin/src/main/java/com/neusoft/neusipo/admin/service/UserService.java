package com.neusoft.neusipo.admin.service;

import com.neusoft.neusipo.admin.entity.User;
import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.base.BaseService;
import com.neusoft.neusipo.core.base.Response;

/**
 * @description: 用户业务接口
 * @author: zhengchj
 * @create: 2019-11-01 09:57
 **/
public interface UserService extends BaseService<User, String> {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Response<String> login(String username, String password);
    /**
     * 查询用户信息，用于shiro用户验证
     * @param username
     * @return
     */
    UserInfo loadByUsername(String username);

}
