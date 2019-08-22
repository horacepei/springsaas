package com.springboot.action.saas.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/*
* 平台用户组建和服务来回传递的数据结构
* */
@Data
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class UserDto implements Serializable {
    //id
    private Long id;
    //用户名
    private String name;
    //用户密码（json返回时不显示，接口规范必须实现）
    @JsonIgnore
    private String password;
    //是否启动
    private Boolean enabled;
    //创建时间,时间戳
    private Long createTime;
    //密码重置时间（时间戳,json返回时不显示，接口规范必须实现）
    @JsonIgnore
    private Long passwordResetDate;
    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled='" + enabled + '\'' +
                ", createTime='" + createTime + '\'' +
                ", passwordResetDate='" + passwordResetDate + '\'' +
                '}';
    }
}
