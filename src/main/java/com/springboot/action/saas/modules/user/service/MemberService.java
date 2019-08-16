package com.springboot.action.saas.modules.user.service;

import com.springboot.action.saas.modules.user.domain.UserMember;

import java.util.List;
/*
* 业务接口定义
* */
public interface MemberService {
    //增加用户
    public Long addMember(UserMember member);
    //更新用户信息
    public void updateMember(UserMember member);
    //通过ID获取用户信息
    public UserMember findMemberById(Integer id);
    //通过用户名称获取用户信息
    public UserMember findMemberByName(String name);
    //获取用户信息列表，带有分页功能
    public List<UserMember> findAllMember();
}
