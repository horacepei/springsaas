package com.springboot.action.saas.modules.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/*
* 平台权限数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名，设置索引
@Table(name="mkt_system_permission")
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class Permission implements Serializable {
    //设置ID主键,定义生成策略为自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //菜单名称，不能为空
    @NotBlank
    @Column(columnDefinition="varchar(100) COMMENT '后台页面展示别名'")
    private String name;
    //hasrole的名称，不能为空
    @NotBlank
    @Column(columnDefinition="varchar(100) COMMENT '权限hasanyrole名称'")
    private String authorize;
    //请求路径
    @Column(columnDefinition="varchar(100) COMMENT '检查的url'")
    private String url;
    //父节点ID
    @Column(name = "parent_id", columnDefinition="varchar(100) COMMENT '权限父节点id'")
    private Long parentId = 0L;

    @Override
    public String toString() {
        return "UserMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorize='" + authorize + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
