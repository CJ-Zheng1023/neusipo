package com.neusoft.neusipo.admin.service;

import com.neusoft.neusipo.admin.entity.Role;
import com.neusoft.neusipo.common.vo.RoleInfo;
import com.neusoft.neusipo.core.base.BaseService;

import java.util.List;

/**
 * @description: 角色业务接口
 * @author: zhengchj
 * @create: 2019-11-02 12:46
 **/
public interface RoleService extends BaseService<Role, String> {
    /**
     * 获取角色和对应可访问资源url pattern
     * @return
     */
    List<RoleInfo> loadRoles();
}
