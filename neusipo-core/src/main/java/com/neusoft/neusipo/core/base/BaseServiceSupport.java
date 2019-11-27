package com.neusoft.neusipo.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @description: 业务接口实现，提供基本正删改查实现
 * @author: zhengchj
 * @create: 2019-11-01 09:28
 **/
public class BaseServiceSupport<T, ID extends Serializable, R extends BaseRepository<T, ID>> {
    @Autowired
    protected R repository;

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    public Optional<T> queryById(ID id){
        return repository.findById(id);
    }

    /**
     * 排序查询
     * @param sortField  排序字段
     * @param sortType   排序类别  ASC|DESC
     * @return
     */
    public List<T> queryListBySort(String sortField, String sortType){
        Sort sort = new Sort(getSortDirection(sortType), sortField);
        return repository.findAll(sort);
    }

    /**
     * 按分页排序查询
     * @param index     页码   从0开始
     * @param size      每页数据个数
     * @param sortField 排序字段
     * @param sortType  排序类别  ASC|DESC
     * @return
     */
    public Page<T> queryListByPage(int index, int size, String sortField, String sortType){
        PageRequest pageRequest = PageRequest.of(index, size, new Sort(getSortDirection(sortType), sortField));
        //PageRequest pageRequest = new PageRequest(index, size, new Sort(getSortDirection(sortType), sortField));
        return repository.findAll(pageRequest);
    }

    /**
     * 保存、修改
     * @param t
     * @return
     */
    @Transactional
    public T save(T t){
        T data = repository.save(t);
        return data;
    }

    /**
     * 批量删除
     * @param ids
     */
    @Transactional
    public void delete(List<ID> ids){
        for(ID id : ids){
            repository.deleteById(id);
        }
    }


    /**
     * 根据排序类型转换成枚举类型
     * @param sortType  排序类别  ASC|DESC
     * @return
     */
    protected Sort.Direction getSortDirection(String sortType){
        return sortType.toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
