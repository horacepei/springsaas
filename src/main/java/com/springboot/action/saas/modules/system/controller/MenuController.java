package com.springboot.action.saas.modules.system.controller;

import com.springboot.action.saas.common.logging.annotation.Log;
import com.springboot.action.saas.modules.system.dto.MenuDto;
import com.springboot.action.saas.modules.system.service.MenuService;
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
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 显示所有信息
     *
     * @return List
     */
    @Log("获取全部菜单列表")
    @RequestMapping(value = "/v1/findAll")
    public List<MenuDto> findAllMember() {
        return menuService.findAllMenu();
    }

    /**
     * 查找id对应的信息
     *
     * @param id
     * @return Member
     */

    // == @RequestMapping(value = "/v1/findById/{id}", method = RequestMethod.GET)
    @Log("获取菜单信息")
    @GetMapping("/v1/findById/{id}")
    public MenuDto findById(@PathVariable("id") Long id) {
        return menuService.findMenuById(id);
    }

    /**
     * 增加信息
     * 数据通过<form>表单者postman模拟验证
     *
     * @param member
     * @return void
     */
    // == @RequestMapping(value="/v1/add", method = RequestMethod.POST)
    @Log("增加菜单")
    @PostMapping("/v1/add")
    public void addInfo(@Validated @RequestBody MenuDto member) {
        System.out.println(member);
        //添加用户
        menuService.addMenu(member);
    }


    /**
     * 修改对应的信息
     *
     * @param member
     * @return void
     */
    // == @RequestMapping(value="/v1/update", method = RequestMethod.PUT)
    @Log("更新菜单")
    @PutMapping("/v1/update")
    public void updateInfo(@Validated @RequestBody MenuDto member) {
        menuService.updateMenu(member);
    }
}
