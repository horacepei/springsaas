package com.springboot.action.saas.modules.user.repository;

import com.springboot.action.saas.modules.user.domain.Member;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberDao {

    private Member testmember;

    public void add(Member member){
        System.out.println("MemberDao.add");
        testmember = new Member();
        testmember.setId(1);
        testmember.setNickname("demo");
    }

    public void save(Member member){
        System.out.println("MemberDao.save");
        testmember.setNickname(member.getNickname());
    }

    public void delete(Integer id){
        System.out.println("MemberDao.delete"+id);
    }

    public Member findOne(Integer id) {
        System.out.println("MemberDao.findOne"+id);
        return testmember;
    }

    public List<Member> findAll() {
        System.out.println("MemberDao.findAll");
        List<Member> list = new ArrayList<Member>();
        Member member1 = new Member();
        member1.setId(1);
        member1.setNickname("demo1");
        list.add(member1);
        Member member2 = new Member();
        member2.setId(2);
        member2.setNickname("demo2");
        list.add(member2);
        return list;
    }
}
