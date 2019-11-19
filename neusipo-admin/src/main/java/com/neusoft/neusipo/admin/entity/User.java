package com.neusoft.neusipo.admin.entity;

import com.neusoft.neusipo.core.base.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @description: 用户数据库映射实体类
 * @author: zhengchj
 * @create: 2019-11-01 09:48
 **/
@Data
@Entity
@Table(name = "ns_user", indexes = {@Index(name = "idx_user_name", columnList = "user_name", unique = true)})
public class User extends BaseEntity {
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "create_time", nullable = false, columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Timestamp createTime;
    @Column(name = "expire_time")
    private Timestamp expireTime;
    /**
     * 0表示未锁定，1表示锁定
     */
    @Column(name = "locked", columnDefinition = "INT default 0", nullable = false)
    private int locked = 0;
    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
