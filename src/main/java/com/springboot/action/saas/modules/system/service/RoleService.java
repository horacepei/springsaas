package com.springboot.action.saas.modules.system.service;

import com.springboot.action.saas.modules.system.dto.RoleDto;

import java.util.List;

/*
* 业务接口定义
* */
public interface RoleService {
    //增加信息
    public Long addRole(RoleDto role);
    //更新信息
    public void updateRole(RoleDto role);
    //通过ID获取信息
    public RoleDto findRoleById(Long id);
    //获取信息列表，带有分页功能
    public List<RoleDto> findAllRole();
}
