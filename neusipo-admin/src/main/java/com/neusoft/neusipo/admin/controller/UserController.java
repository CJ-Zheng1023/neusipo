package com.neusoft.neusipo.admin.controller;

import com.neusoft.neusipo.admin.entity.User;
import com.neusoft.neusipo.admin.service.UserService;
import com.neusoft.neusipo.core.annotation.SendMessage;
import com.neusoft.neusipo.core.base.BaseController;
import com.neusoft.neusipo.core.base.Response;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 用户模块控制器
 * @author: zhengchj
 * @create: 2019-11-01 10:01
 **/
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, String, UserService> {
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public Response<String> login(@RequestParam String username, @RequestParam String password){
        return service.login(username, password);
    }
}
