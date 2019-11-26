package com.neusoft.neusipo.core.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @description: 业务接口，提供基本增删改查接口
 * @author: zhengchj
 * @create: 2019-10-31 17:11
 * @param <T>  数据库映射实体类
 * @param <ID> 主键
 **/


public interface BaseService<T, ID extends Serializable> {
    Optional<T> queryById(ID id);
    List<T> queryListBySort(String sortField, String sortType);
    Page<T> queryListByPage(int index, int size, String sortField, String sortType);
    T save(T t);
    void delete(List<ID> ids);
}
