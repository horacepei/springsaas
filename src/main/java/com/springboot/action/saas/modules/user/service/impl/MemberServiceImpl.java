package com.springboot.action.saas.modules.user.service.impl;

import com.springboot.action.saas.modules.user.domain.UserMember;
import com.springboot.action.saas.modules.user.dto.UserDto;
import com.springboot.action.saas.modules.user.repository.UserMemberRepository;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * 业务接口是实现
 * */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private UserMemberRepository userMemberRepository;
    @Override
    public Long addMember(UserDto member) {
        //检查用户名是否已经存在
        if(userMemberRepository.findByName(member.getName()) != null){
            //走存在处理分支
            return -1L;
        }
        UserMember userMember = new UserMember();
        //用户姓名
        userMember.setName(member.getName());
        //加密密码
        userMember.setPassWord(member.getPassword());
        //加入到数据库
        userMemberRepository.saveAndFlush(userMember);
        return userMember.getId();
    }

    @Override
    public UserDto findMemberById(Long id) {
        UserDto userDto = new UserDto();
        Optional<UserMember> userMemberOptional = userMemberRepository.findById(id);

        if(!userMemberOptional.isPresent()){
            //用户存在，走不存在处理
            return userDto;
        }

        UserMember userMember = userMemberOptional.get();

        userDto.setName(userMember.getName());
        userDto.setPassword(userMember.getPassWord());

        return userDto;
    }

    @Override
    public UserDto findMemberByName(String name) {

        UserMember userMember = userMemberRepository.findByName(name);

        UserDto userDto = new UserDto();
        userDto.setName(userMember.getName());
        userDto.setPassword(userMember.getPassWord());

        return userDto;
    }

    @Override
    public List<UserDto> findAllMember() {
        List<UserMember> userMemberList =  userMemberRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();;
        for (int i = 0; i < userMemberList.size(); i++) {
            UserMember userMember = (UserMember) userMemberList.get(i);

            UserDto userDto = new UserDto();
            userDto.setName(userMember.getName());
            userDto.setPassword(userMember.getPassWord());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public void updateMember(UserDto member) {
        UserMember userMember = new UserMember();
        //加密密码
        userMember.setPassWord(member.getPassword());

        userMemberRepository.saveAndFlush(userMember);
    }
}
