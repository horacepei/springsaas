package com.springboot.action.saas.modules.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 平台用户组建和服务来回传递的数据结构
* */
@Data
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class MenuDto implements Serializable {
    private Long id;
    private String name;
    private Boolean isUrl;
    private String path;
    private Long parentId;
    private Long sort;
    private String icon;
    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", isUrl='" + isUrl + '\'' +
                ", path='" + path + '\'' +
                ", parentId='" + parentId + '\'' +
                ", sort='" + sort + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
