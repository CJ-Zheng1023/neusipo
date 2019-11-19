package com.neusoft.neusipo.core.base;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @description: 数据库表映射实体类基类
 * @author: zhengchj
 * @create: 2019-10-31 15:57
 **/
@MappedSuperclass
@Data
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
}
