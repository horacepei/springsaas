package com.springboot.action.saas.modules.security.dto;

import lombok.Data;

import java.io.Serializable;

//用户认证成功后，返回用户信息和jwt的token
@Data
public class LoginInfoDto implements Serializable {

    private final String token;

    private final UserDetailsDto user;

    public LoginInfoDto(String token, UserDetailsDto user) {
        this.token = token;
        this.user = user;
    }
}
