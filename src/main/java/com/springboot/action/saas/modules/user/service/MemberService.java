package com.springboot.action.saas.modules.user.service;

import com.springboot.action.saas.modules.user.domain.UserMember;
import com.springboot.action.saas.modules.user.dto.UserDto;

import java.util.List;
/*
* 业务接口定义
* */
public interface MemberService {
    //增加用户
    public Long addMember(UserDto member);
    //更新用户信息
    public void updateMember(UserDto member);
    //通过ID获取用户信息
    public UserDto findMemberById(Long id);
    //通过用户名称获取用户信息
    public UserDto findMemberByName(String name);
    //获取用户信息列表，带有分页功能
    public List<UserDto> findAllMember();
}
