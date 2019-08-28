package com.springboot.action.saas.modules.security.service;

import com.springboot.action.saas.modules.system.domain.Role;

import java.util.List;

/*
* 业务接口定义
* */
public interface RoleService {
    //增加角色
    public Long addRole(Role role);
    //删除角色
    public Boolean delMember(Role role);
    //通过ID获取用户信息
    public Role findRoleById(Long id);
    //获取角色信息列表，带有分页功能
    public List<Role> findAllRole();
}
