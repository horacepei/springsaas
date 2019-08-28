package com.springboot.action.saas.modules.system.service;

import com.springboot.action.saas.modules.system.dto.PermissionDto;

import java.util.List;

/*
* 业务接口定义
* */
public interface PermissionService {
    //增加
    public Long addPermission(PermissionDto permissionDto);
    //更新
    public void updatePermission(PermissionDto permissionDto);
    //通过ID获取信息
    public PermissionDto findPermissionById(Long id);
    //获取信息列表，带有分页功能
    public List<PermissionDto> findAllPermission();
}
