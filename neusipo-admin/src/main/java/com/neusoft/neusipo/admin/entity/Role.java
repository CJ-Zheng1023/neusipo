package com.neusoft.neusipo.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.neusipo.core.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 角色数据库实体类
 * @author: zhengchj
 * @create: 2019-11-02 10:39
 **/
@Data
@Entity
@Table(name = "ns_role", indexes = {@Index(name = "idx_role_name", columnList = "name", unique = true)})
public class Role extends BaseEntity {
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "name", nullable = false)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Url> urls = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "ns_role_m_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menus = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<User> users = new ArrayList<>();
}
