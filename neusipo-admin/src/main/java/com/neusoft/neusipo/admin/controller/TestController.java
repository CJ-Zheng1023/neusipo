package com.neusoft.neusipo.admin.controller;

import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhengchj
 * @create: 2019-11-19 16:42
 **/
@RestController
public class TestController {
    @Autowired
    private RedisRepository redisRepository;
    @RequestMapping("/test")
    public boolean test(){
        String key = "abcd";
        redisRepository.set(key, "vvvv");
        String v = (String) redisRepository.get(key);
        System.out.println(v);
        return true;
    }
    @RequestMapping("/test1")
    public int test1(){
        return 1;
    }
    @RequestMapping("/test2")
    public String test2(){
        return null;
    }
    @RequestMapping("/test3")
    public void test3(){
        System.out.println("123123");
    }
    @RequestMapping("/test4")
    public UserInfo test4(){
        return null;
    }
    @RequestMapping("/test5")
    public UserInfo test5(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("郑成杰");
        return userInfo;
    }
    @RequestMapping("/test6")
    public String test6(){
        return "郑成杰";
    }
}
