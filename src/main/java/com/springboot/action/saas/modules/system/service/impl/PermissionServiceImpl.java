package com.springboot.action.saas.modules.system.service.impl;

import com.springboot.action.saas.common.utils.EncryptionUtils;
import com.springboot.action.saas.modules.system.domain.Permission;
import com.springboot.action.saas.modules.system.dto.PermissionDto;
import com.springboot.action.saas.modules.system.repository.PermissionRepository;
import com.springboot.action.saas.modules.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * 业务接口是实现
 * */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Long addPermission(PermissionDto permissionDto) {
        //检查是否已经存在
        if (null != permissionRepository.findByName(permissionDto.getName())) {
            //走存在处理分支
            return -1L;
        }
        Permission permission = new Permission();
        permission.setName(permissionDto.getName());
        permission.setAuthorize(permissionDto.getAuthorize());
        permission.setUrl(permissionDto.getUrl());
        permission.setParentId(permissionDto.getParentId());

        permissionRepository.saveAndFlush(permission);
        return permission.getId();
    }

    @Override
    public void updatePermission(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setName(permissionDto.getName());
        permission.setAuthorize(permissionDto.getAuthorize());
        permission.setUrl(permissionDto.getUrl());
        permission.setParentId(permissionDto.getParentId());

        permissionRepository.save(permission);
    }

    @Override
    public PermissionDto findPermissionById(Long id) {

        Optional<Permission> permissionOptional = permissionRepository.findById(id);

        if (!permissionOptional.isPresent()) {
            //用户不存在，走不存在处理
            return null;
        }
        //domain to dto
        Permission permission = permissionOptional.get();
        PermissionDto permissionDto = new PermissionDto();
        permissionDto.setId(permission.getId());
        permissionDto.setName(permission.getName());
        permissionDto.setAuthorize(permission.getAuthorize());
        permissionDto.setUrl(permission.getUrl());
        permissionDto.setParentId(permission.getParentId());

        return permissionDto;
    }

    @Override
    public List<PermissionDto> findAllPermission() {
        List<Permission> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        ;
        for (int i = 0; i < permissionList.size(); i++) {
            Permission permission = (Permission) permissionList.get(i);
            //domain to dto
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setId(permission.getId());
            permissionDto.setName(permission.getName());
            permissionDto.setAuthorize(permission.getAuthorize());
            permissionDto.setUrl(permission.getUrl());
            permissionDto.setParentId(permission.getParentId());

            permissionDtoList.add(permissionDto);
        }
        return permissionDtoList;
    }
}
