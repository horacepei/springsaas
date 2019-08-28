package com.springboot.action.saas.modules.system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 平台权限数据表实体类
* */
@Data
//lombok 注解，NoArgsConstructor 无参数构造函数
@NoArgsConstructor
public class PermissionDto implements Serializable {
    private Long id;
    private String name;
    private String authorize;
    private String url;
    private Long parentId;

    @Override
    public String toString() {
        return "UserMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorize='" + authorize + '\'' +
                ", url='" + url + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
