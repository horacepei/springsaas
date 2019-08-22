package com.springboot.action.saas.modules.security.service.impl;

import com.springboot.action.saas.modules.security.dto.UserDetailsDto;
import com.springboot.action.saas.modules.user.dto.UserDto;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

/*
* UserDetailsService接口实现
* */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    /*
     *  通过用户名称，查找用户信息，认证相关的逻辑都在loadUserByUsername方法中进行
     * */
    @Override
    public UserDetails loadUserByUsername(String username) {

        UserDto userDto = memberService.findMemberByName(username);
        if (userDto == null) {
            //如果没找到用户信息，抛出用户没找到异常
            throw new EntityNotFoundException("name：" + username + " not found");
        } else {
            //如果找到，封装该用户的用户名、密码、角色
            return createUser(userDto);
        }
    }

    /*
     *  封装认证需要的UserDetails该用户的用户名、密码、角色
     * */
    public UserDetails createUser(UserDto user) {
        //用户名
        //密码
        //角色list 当前版本先实现密码和帐号验证，先不增加角色控制，实际上要在角色list中增加增加new SimpleGrantedAuthority();
        Collection<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        return new UserDetailsDto(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEnabled(),
                user.getCreateTime(),
                user.getPasswordResetDate(),
                authList);
    }
}
