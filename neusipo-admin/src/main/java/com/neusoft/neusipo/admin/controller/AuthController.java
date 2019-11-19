package com.neusoft.neusipo.admin.controller;

import com.neusoft.neusipo.admin.service.RoleService;
import com.neusoft.neusipo.admin.service.UserService;
import com.neusoft.neusipo.common.vo.RoleInfo;
import com.neusoft.neusipo.common.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户认证接口，用于shiro用户认证
 * @author: zhengchj
 * @create: 2019-11-02 10:33
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping("/load/user")
    public UserInfo loadByUsername(@RequestParam String username){
        return userService.loadByUsername(username);
    }
    @GetMapping("/load/roles")
    public List<RoleInfo> loadRoles(){
        return roleService.loadRoles();
    }
}
