package com.neusoft.neusipo.common.vo;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 用户信息实体
 * @author: zhengchj
 * @create: 2019-11-02 11:19
 **/
@Data
public class UserInfo extends BaseVO{
    private String id;
    private String username;
    private String role;
    //TODO
    private List<String> menus = new ArrayList<>();
}
