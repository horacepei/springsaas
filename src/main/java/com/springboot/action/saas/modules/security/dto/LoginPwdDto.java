package com.springboot.action.saas.modules.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


//用户帐号密码登陆，用户和密码
@Data
//lombok 注解，NoArgsConstructor 走动生成无参数构造函数
@NoArgsConstructor
public class LoginPwdDto implements Serializable {
    //用户帐号
    private String username;
    //用户密码
    private String password;
    //对象字符串输出
    @Override
    public String toString() {
        return "{username="
                + username
                + ", password=******}";
    }
}
