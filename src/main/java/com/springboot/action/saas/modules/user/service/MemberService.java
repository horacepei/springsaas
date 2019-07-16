package com.springboot.action.saas.modules.user.service;

import com.springboot.action.saas.modules.user.po.Member;

import java.util.List;
/*
* 业务接口定义
* */
public interface MemberService {
    public void addMember(Member member);
    public void deleteMember(Integer id);
    public void updateMember(Member member);
    public Member findMember(Integer id);
    public List<Member> findAllMember();
}
