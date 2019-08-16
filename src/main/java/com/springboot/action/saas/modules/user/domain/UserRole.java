package com.springboot.action.saas.modules.user.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

/*
* 用户和角色数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名,增加索引，通过用户查权限，通过权限查有那些用户
@Table(name="mkt_user_role",
        indexes = {
            @Index(name = "idx_user_id",  columnList="user_id", unique = true),
            @Index(name = "idx_role_id",  columnList="role_id", unique = true)})
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class UserRole {
    //设置ID主键,定义生成策略为自增，从数据库表设计角度，增加id也是有好处的
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //用户id,定义字段名称
    @Column(name = "user_id")
    private Long userId;
    //角色id,定义字段名称
    @Column(name = "role_id")
    private Long roleId;
}
