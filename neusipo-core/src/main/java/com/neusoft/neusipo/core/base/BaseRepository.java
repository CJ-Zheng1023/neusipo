package com.neusoft.neusipo.core.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @description: jpa接口
 * @author: zhengchj
 * @create: 2019-10-31 16:30
 **/
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T findById(ID id);
}
