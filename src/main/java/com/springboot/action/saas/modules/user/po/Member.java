package com.springboot.action.saas.modules.user.po;

import lombok.Data;
/*
* 平台用户信息数据
* */
//这里使用@Data注解，是lombok包中的，自动生成一些操作成员变量的方法
@Data
public class Member {
    //用户id
    private Integer id;
    //用户手机号
    private String phone;
    //用户密码
    private String password;
    //用户昵称
    private String nickname;
    //邮箱
    private String email;
    //注册时间
    private Integer ctime;
    //更新时间
    private Integer utime;
    //最近登录时间
    private Integer last_login_time;
    //最近登录ip
    private String last_login_ip;
    //邀请码
    private String invite_code;
    //激活（绑定手机）状态标示
    private Integer is_active;
    //删除状态标识
    private Integer is_delete;
}
