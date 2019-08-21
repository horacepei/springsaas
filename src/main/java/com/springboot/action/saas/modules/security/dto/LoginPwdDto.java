package com.springboot.action.saas.modules.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


//用户帐号密码登陆，用户和密码
@Data
public class LoginPwdDto implements Serializable {
    //用户帐号
    @NotBlank
    private final String username;
    //用户密码
    @NotBlank
    private final String password;
    //对象字符串输出
    @Override
    public String toString() {
        return "{username="
                + username
                + ", password=******}";
    }
}
