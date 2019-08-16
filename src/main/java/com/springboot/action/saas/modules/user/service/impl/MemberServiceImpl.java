package com.springboot.action.saas.modules.user.service.impl;

import com.springboot.action.saas.modules.user.domain.UserMember;
import com.springboot.action.saas.modules.user.repository.UserMemberRepository;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 业务接口是实现
 * */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private UserMemberRepository userMemberRepository;
    @Override
    public Long addMember(UserMember member) {
        //检查用户名是否已经存在
        if(userMemberRepository.findByName(member.getName()) != null){
            //走存在处理分支
            return -1L;
        }
        //加密密码
        //加入到数据库
        userMemberRepository.saveAndFlush(member);
        return member.getId();
    }

    @Override
    public void updateMember(UserMember member) {
        userMemberRepository.saveAndFlush(member);
    }

    @Override
    public UserMember findMemberById(Integer id) {

        return userMemberRepository.findById(id);
    }

    @Override
    public UserMember findMemberByName(String name) {

        return userMemberRepository.findByName(name);
    }

    @Override
    public List<UserMember> findAllMember() {
        return userMemberRepository.findAll();
    }
}
