package com.neusoft.neusipo.admin.service.impl;

import com.neusoft.neusipo.admin.entity.Role;
import com.neusoft.neusipo.admin.entity.Url;
import com.neusoft.neusipo.admin.repository.RoleRepository;
import com.neusoft.neusipo.admin.service.RoleService;
import com.neusoft.neusipo.common.vo.RoleInfo;
import com.neusoft.neusipo.core.base.BaseServiceSupport;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 角色业务接口实现
 * @author: zhengchj
 * @create: 2019-11-02 12:48
 **/
@Service
public class RoleServiceImpl extends BaseServiceSupport<Role, String, RoleRepository> implements RoleService {
    /**
     * 获取角色和对应可访问资源url pattern
     *
     * @return
     */
    @Override
    public List<RoleInfo> loadRoles() {
        List<RoleInfo> roleInfos = new ArrayList<>();
        List<Role> roles = repository.findAll();
        for(Role role : roles){
            List<Url> urls = role.getUrls();
            if(urls.size() == 0){
                continue;
            }
            List<String> patterns = new ArrayList<>();
            for(Url url : urls){
                patterns.add(url.getPattern());
            }
            roleInfos.add(new RoleInfo(role.getName(), patterns));
        }
        return roleInfos;
    }
}
