package com.neusoft.neusipo.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neusoft.neusipo.core.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 菜单数据库实体类
 * @author: zhengchj
 * @create: 2019-11-02 10:42
 **/
@Entity
@Data
@Table(name = "ns_menu")
public class Menu extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "parent_id")
    private String parentId;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private List<Menu> children = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
