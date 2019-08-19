package com.springboot.action.saas.modules.user.dto;

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
    //用户名
    private String name;
    //用户密码
    private String password;

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
