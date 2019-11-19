package com.neusoft.neusipo.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 角色信息实体类
 * @author: zhengchj
 * @create: 2019-11-02 12:28
 **/
@Data
public class RoleInfo {
    public RoleInfo(){}
    public RoleInfo(String name, List<String> patterns){
        this.name = name;
        this.patterns = patterns;
    }
    private String name;
    private List<String> patterns = new ArrayList<>();
}
