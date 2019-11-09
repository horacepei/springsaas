package com.springboot.action.saas.modules.user.service.impl;

import com.springboot.action.saas.modules.user.domain.UserMember;
import com.springboot.action.saas.modules.user.dto.UserDto;
import com.springboot.action.saas.modules.user.repository.UserMemberRepository;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        if(null != userMemberRepository.findByName(member.getName())){
            //走存在处理分支
            return -1L;
        }
        UserMember userMember = new UserMember();
        //用户姓名
        userMember.setName(member.getName());
        //加密密码
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        userMember.setPassWord(encode.encode(member.getPassword()));
        //设置创建时间
        userMember.setCreateTime(System.currentTimeMillis());
        //加入到数据库
        userMemberRepository.saveAndFlush(userMember);
        return userMember.getId();
    }

    @Override
    public UserDto findMemberById(Long id) {
        UserDto userDto = new UserDto();
        Optional<UserMember> userMemberOptional = userMemberRepository.findById(id);

        if(!userMemberOptional.isPresent()){
            //用户不存在，走不存在处理
            return null;
        }
        //domain to dto
        UserMember userMember = userMemberOptional.get();
        userDto.setId(userMember.getId());
        userDto.setName(userMember.getName());
        userDto.setPassword(userMember.getPassWord());
        userDto.setEnabled(userMember.getEnabled());
        userDto.setCreateTime(userMember.getCreateTime());
        userDto.setPasswordResetDate(userMember.getPasswordResetDate());

        return userDto;
    }

    @Override
    public UserDto findMemberByName(String name) {
        UserMember userMember = userMemberRepository.findByName(name);
        if (null == userMember) {
            //用户不存在，走不存在处理
            return null;
        }
        //domain to dto
        UserDto userDto = new UserDto();
        userDto.setId(userMember.getId());
        userDto.setName(userMember.getName());
        userDto.setPassword(userMember.getPassWord());
        userDto.setEnabled(userMember.getEnabled());
        userDto.setCreateTime(userMember.getCreateTime());
        userDto.setPasswordResetDate(userMember.getPasswordResetDate());

        return userDto;
    }

    @Override
    public List<UserDto> findAllMember() {
        List<UserMember> userMemberList =  userMemberRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();;
        for (int i = 0; i < userMemberList.size(); i++) {
            UserMember userMember = (UserMember) userMemberList.get(i);
            //domain to dto
            UserDto userDto = new UserDto();
            userDto.setId(userMember.getId());
            userDto.setName(userMember.getName());
            userDto.setEnabled(userMember.getEnabled());
            userDto.setCreateTime(userMember.getCreateTime());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public void updateMember(UserDto member) {
        UserMember userMember = new UserMember();
        //加密密码
        userMember.setId(member.getId());
        userMember.setName(member.getName());
        userMember.setPassWord(member.getPassword());
        userMember.setEnabled(member.getEnabled());
        userMember.setCreateTime(member.getCreateTime());
        userMemberRepository.saveAndFlush(userMember);
    }
}
