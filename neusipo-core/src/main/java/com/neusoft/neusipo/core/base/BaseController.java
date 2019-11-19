package com.neusoft.neusipo.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @description: 控制器基类，提供基本增删改查
 * @author: zhengchj
 * @create: 2019-11-01 09:39
 **/
public class BaseController<T, ID extends Serializable, S extends BaseService<T, ID>> {
    @Autowired(required = false)
    protected S service;
    @GetMapping("/query/{id}")
    public Optional<T> queryById(@PathVariable("id") ID id){
        return Optional.ofNullable(service.queryById(id));
    }
    @GetMapping("/list/sort")
    public List<T> queryListBySort(@RequestParam(defaultValue = "id", required = false) String sortField,
                                   @RequestParam(defaultValue = "DESC", required = false) String sortType){
        return service.queryListBySort(sortField, sortType);
    }
    @GetMapping("/list/page")
    public Page<T> queryListByPage(@RequestParam(defaultValue = "0", required = false) int index,
                                   @RequestParam(defaultValue = "10", required = false) int size,
                                   @RequestParam(defaultValue = "id", required = false) String sortField,
                                   @RequestParam(defaultValue = "DESC", required = false) String sortType){
        return service.queryListByPage(index, size, sortField, sortType);
    }
    @PostMapping("/save")
    public Optional<T> save(@RequestBody T t){
        return Optional.of(service.save(t));
    }
    @PostMapping("/delete")
    public void delete(@RequestBody List<ID> ids){
        service.delete(ids);
    }
}
