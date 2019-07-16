package com.springboot.action.saas.modules.user.service.impl;

import com.springboot.action.saas.modules.user.dao.MemberDao;
import com.springboot.action.saas.modules.user.po.Member;
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
    private MemberDao memberDao;
    @Override
    public void addMember(Member member) {
        memberDao.add(member);
    }

    @Override
    public void deleteMember(Integer id) {
        memberDao.delete(id);
    }

    @Override
    public void updateMember(Member member) {
        memberDao.save(member);
    }

    @Override
    public Member findMember(Integer id) {
        return memberDao.findOne(id);
    }

    @Override
    public List<Member> findAllMember() {
        return memberDao.findAll();
    }
}
