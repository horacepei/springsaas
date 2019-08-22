package com.springboot.action.saas.modules.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//这个类，一定比userDto要数据更全面，因为UserDetails在认证后UserDetailsServiceImpl的
//loadUserByUsername函数获取的信息
@Getter
//生成一个全参数的构造函数
@AllArgsConstructor
public class UserDetailsDto implements UserDetails {
    //用户idjson返回时不显示）
    //在json序列化时将java bean中的一些属性忽略掉
    @JsonIgnore
    private final Long id;
    //用户名称（接口规范必须实现）
    private final String username;
    //用户密码（json返回时不显示，接口规范必须实现）
    @JsonIgnore
    private final String password;
    //是否禁用
    private final Boolean enabled;

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    //创建时间
    private final Long createTime;
    //密码重置时间（json返回时不显示）
    @JsonIgnore
    private final Long passwordResetDate;
    //用户权限列表（接口规范必须实现）
    @JsonIgnore
    private final Collection<GrantedAuthority> authorities;
    //（接口规范必须实现）
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //（接口规范必须实现）
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //（接口规范必须实现）
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
