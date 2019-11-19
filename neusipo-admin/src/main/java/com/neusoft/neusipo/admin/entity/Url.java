package com.neusoft.neusipo.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.neusipo.core.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @description: 可访问资源数据库实体类
 * @author: zhengchj
 * @create: 2019-11-02 10:43
 **/
@Data
@Entity
@Table(name = "ns_url")
public class Url extends BaseEntity {
    @Column(name = "pattern", nullable = false)
    private String pattern;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;
    @Column(name = "role_id")
    private String roleId;
}
