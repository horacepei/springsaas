package com.springboot.action.saas.modules.security.controller;

import com.springboot.action.saas.common.logging.annotation.Log;
import com.springboot.action.saas.modules.security.dto.LoginInfoDto;
import com.springboot.action.saas.modules.security.dto.LoginPwdDto;
import com.springboot.action.saas.modules.security.dto.UserDetailsDto;
import com.springboot.action.saas.modules.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *  restful 风格接口
 * */
//@RestController 代替 @Controller,省略以后的 @ResponseBody
@RestController
//处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("/security")
public class SecurityController {
    //jwt工具对象,根据类型来查找和自动装配元素的
    @Autowired
    private JwtUtil jwtUtil;
    //认证用户信息对象
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * 用户帐号，密码登陆
     * @param loginPwdDto 用户的帐号和密码
     * @return LoginInfoDto
     */
    @Log("帐号登陆")
    @PostMapping(value = "/pwdlogin")
    public LoginInfoDto pwdlgoin(@Validated @RequestBody LoginPwdDto loginPwdDto) {
        //获取用户认证信息
        final UserDetailsDto userDetailsDto = (UserDetailsDto)userDetailsService.loadUserByUsername(loginPwdDto.getUsername());
        //判定密码是否正确
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        if (!userDetailsDto.getPassword().equals(encode.encode(loginPwdDto.getPassword()))) {
            //密码错误处理，抛异常
            throw new AccountExpiredException("密码错误");
        }
        //判定用户是否启动
        if (!userDetailsDto.isEnabled()) {
            //处理帐号禁用，抛异常
            throw new AccountExpiredException("帐号被禁用");
        }
        //生成token
        String token = jwtUtil.generateToken(userDetailsDto);
        //返回认证信息对象
        return new LoginInfoDto(token, userDetailsDto);
    }
}
