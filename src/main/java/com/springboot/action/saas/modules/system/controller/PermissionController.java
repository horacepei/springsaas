package com.springboot.action.saas.modules.system.controller;

import com.springboot.action.saas.common.logging.annotation.Log;
import com.springboot.action.saas.modules.user.dto.UserDto;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *  restful 风格接口
 * */
//@RestController 代替 @Controller,省略以后的 @ResponseBody
@RestController
//处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private MemberService memberService;

    /**
     * 显示所有Member，请求url:"http://xxx/member/v1/findAll"
     *
     * @return List
     */
    @Log("获取全部用户列表")
    @RequestMapping(value = "/v1/findAll")
    public List<UserDto> findAllMember() {
        return memberService.findAllMember();
    }

    /**
     * 查找id对应的Member信息，请求url:"http://xxx/member/v1/findById/1"
     *
     * @param id
     * @return Member
     */

    // == @RequestMapping(value = "/v1/findById/{id}", method = RequestMethod.GET)
    @GetMapping("/v1/findById/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return memberService.findMemberById(id);
    }

    /**
     * 查找id对应的Member信息，请求url:"http://xxx/member/v1/findByName/1"
     *
     * @param name
     * @return Member
     */

    // == @RequestMapping(value = "/v1/findByName/{name}", method = RequestMethod.GET)
    @GetMapping("/v1/findByName/{name}")
    public UserDto findByName(@PathVariable("name") String name) {
        return memberService.findMemberByName(name);
    }

    /**
     * 增加member信息，请求url:"http://xxx/member/v1/add"
     * 数据通过<form>表单者postman模拟验证
     *
     * @param member
     * @return void
     */
    // == @RequestMapping(value="/v1/add", method = RequestMethod.POST)
    @Log("增加用户")
    @PostMapping("/v1/add")
    public void addMember(@Validated @RequestBody UserDto member) {
        System.out.println(member);
        //参数检测
        //添加用户
        memberService.addMember(member);
    }


    /**
     * 修改对应的Member，请求url:"http://xxx/member/v1/update"
     * 验证：可以通过 jquery的 $.ajax方法或者postman，并type="put",同时注意data形式——A=a&B=b&C=c
     *
     * @param member
     * @return void
     */
    // == @RequestMapping(value="/v1/update", method = RequestMethod.PUT)
    @PutMapping("/v1/update")
    public void updateMember(@Validated @RequestBody UserDto member) {
        memberService.updateMember(member);
    }
}
