package com.neusoft.neusipo.admin.repository;

import com.neusoft.neusipo.admin.entity.User;
import com.neusoft.neusipo.core.base.BaseRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 用户数据库操作接口
 * @author: zhengchj
 * @create: 2019-11-01 09:55
 **/
@Repository
public interface UserRepository extends BaseRepository<User, String> {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
