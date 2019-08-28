package com.springboot.action.saas.modules.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/*
* 平台菜单数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名，设置索引
@Table(name="mkt_system_menu",
        indexes = {@Index(name = "idx_name",  columnList="name", unique = true)})
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class Menu implements Serializable {
    //设置ID主键,定义生成策略为自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //菜单名称，不能为空
    @NotBlank
    @Column(columnDefinition="varchar(100) COMMENT '菜单名称'")
    private String name;
    //菜单是否为跳转地址
    @Column(name = "is_url", columnDefinition="varchar(100) COMMENT '菜单是否为url地址 '")
    private Boolean isUrl = Boolean.FALSE;
    //请求路径
    @Column(columnDefinition="varchar(100) COMMENT '请求方法的路径'")
    private String path;
    //父节点ID
    @Column(name = "parent_id", columnDefinition="varchar(100) COMMENT '菜单父节点id'")
    private Long parentId = 0L;
    //排序
    @Column(columnDefinition="varchar(100) COMMENT '显示排序'")
    private Long sort = 0L;
    //小图标
    @Column(columnDefinition="varchar(100) COMMENT 'icon'")
    private String icon;

    @Override
    public String toString() {
        return "UserMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isUrl='" + isUrl + '\'' +
                ", path='" + path + '\'' +
                ", parentId='" + parentId + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
