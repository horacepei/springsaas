package com.springboot.action.saas.modules.system.service;

import com.springboot.action.saas.modules.system.dto.MenuDto;

import java.util.List;

/*
* 业务接口定义
* */
public interface MenuService {
    //增加
    public Long addMenu(MenuDto member);
    //更新
    public void updateMenu(MenuDto member);
    //通过ID获取信息
    public MenuDto findMenuById(Long id);
    //获取信息列表，带有分页功能
    public List<MenuDto> findAllMenu();
}
