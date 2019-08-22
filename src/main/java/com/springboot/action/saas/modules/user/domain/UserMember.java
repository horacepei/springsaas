package com.springboot.action.saas.modules.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/*
* 平台用户数据表实体类
* */
@Data
//jpa 实体
@Entity
//表名，设置索引
@Table(name="mkt_user_member",
        indexes = {@Index(name = "idx_name",  columnList="name", unique = true)})
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class UserMember implements Serializable {
    //设置ID主键,定义生成策略为自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //帐号，不能为空
    @NotBlank
    private String name;
    //密码，不能为空, 长度为64
    @NotBlank
    @Column(name = "password", length = 128)
    private String passWord;
    //是否启动
    private Boolean enabled = Boolean.TRUE;
    //创建时间,时间戳
    @Column(name = "create_time")
    private Long createTime = 0L;
    //密码重置时间（时间戳）
    @Column(name = "password_reset_date")
    private Long passwordResetDate = 0L;
    @Override
    public String toString() {
        return "UserMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passWord='" + passWord + '\'' +
                ", enabled='" + enabled + '\'' +
                ", createTime='" + createTime + '\'' +
                ", passwordResetDate='" + passwordResetDate + '\'' +
                '}';
    }
}
