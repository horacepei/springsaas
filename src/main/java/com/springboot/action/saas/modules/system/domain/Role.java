package com.springboot.action.saas.modules.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* 角色数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名
@Table(name="mkt_system_role")
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class Role {
    //设置ID主键,定义生成策略为自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //描述
    private String name;
}
