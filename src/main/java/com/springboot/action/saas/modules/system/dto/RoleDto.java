package com.springboot.action.saas.modules.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 平台角色DTO数据结构
* */
@Data
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class RoleDto implements Serializable {
    private Long id;
    private String name;
    @Override
    public String toString() {
        return "RoleDto{" +
                "id='" + id + '\'' +
                ",name='" + name + '\'' +
                '}';
    }
}
