package com.springboot.action.saas.modules.system.service.impl;

import com.springboot.action.saas.common.utils.EncryptionUtils;
import com.springboot.action.saas.modules.system.domain.Role;
import com.springboot.action.saas.modules.system.dto.RoleDto;
import com.springboot.action.saas.modules.system.repository.RoleRepository;
import com.springboot.action.saas.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * 业务接口是实现
 * */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Long addRole(RoleDto roleDto) {
        //检查用户名是否已经存在
        if(null != roleRepository.findByName(roleDto.getName())){
            //走存在处理分支
            return -1L;
        }
        Role role = new Role();
        role.setName(roleDto.getName());
        //加入到数据库
        roleRepository.saveAndFlush(role);
        return role.getId();
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        roleRepository.save(role);
    }

    @Override
    public RoleDto findRoleById(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(!roleOptional.isPresent()){
            //不存在处理
            return null;
        }
        //domain to dto
        Role role = roleOptional.get();

        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        return roleDto;
    }

    @Override
    public List<RoleDto> findAllRole() {
        List<Role> roleList =  roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();;
        for (int i = 0; i < roleList.size(); i++) {
            Role role = (Role)roleList.get(i);
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());

            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
