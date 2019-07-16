package com.springboot.action.saas.modules.user.controller;

import com.springboot.action.saas.modules.user.po.Member;
import com.springboot.action.saas.modules.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
*  restful 风格接口
* */
//@RestController 代替 @Controller,省略以后的 @ResponseBody
@RestController
//处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 显示所有Member，请求url:"http://xxx/member/findall"
     *
     * @return List
     */
    @RequestMapping(value = "/findall")
    public List<Member> findAllMember() {
        return memberService.findAllMember();
    }


    /**
     * 查找id对应的Member信息，请求url:"http://xxx/member/findone/1"
     *
     * @param id
     * @return Member
     */

    // == @RequestMapping(value = "/findone/{id}", method = RequestMethod.GET)
    @GetMapping("/findone/{id}")
    public Member findMemberRestful(@PathVariable("id") Integer id) {
        return memberService.findMember(id);
    }


    /**
     * 删除id对应的Member，请求url:"http://xxx/member/deleteone/4"
     * 可以通过 jquery的 $.ajax者postman方法，并type="delete"
     *
     * @param id
     *
     * @return void
     */
    // == @RequestMapping(value = "/deleteone/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/deleteone/{id}")
    public void deleteStudentRestful(@PathVariable("id") Integer id) {
        memberService.deleteMember(id);
    }


    /**
     * 增加member信息，请求url:"http://localhost/member/addone"
     * 数据通过<form>表单者postman模拟验证
     *
     * @param member
     *
     * @return void
     */
    // == @RequestMapping(value="/addone",method=RequestMethod.POST)
    @PostMapping("/addone")
    public void addStudentRestful(Member member) {
        memberService.addMember(member);
    }


    /**
     * 修改对应的Member，请求url:"http://localhost/member/updateone"
     * 验证：可以通过 jquery的 $.ajax方法或者postman，并type="put",同时注意data形式——A=a&B=b&C=c
     *
     * @param member
     *
     * @return void
     */
    // == @RequestMapping(value="/addone",method=RequestMethod.PUT)
    @PutMapping("/updateone")
    public void updateStudentRestful(Member member) {
        memberService.updateMember(member);
    }
}