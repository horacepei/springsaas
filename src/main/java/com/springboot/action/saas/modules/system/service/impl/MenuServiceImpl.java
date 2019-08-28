package com.springboot.action.saas.modules.system.service.impl;

import com.springboot.action.saas.modules.system.domain.Menu;
import com.springboot.action.saas.modules.system.dto.MenuDto;
import com.springboot.action.saas.modules.system.repository.MenuRepository;
import com.springboot.action.saas.modules.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * 业务接口是实现
 * */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Override
    public Long addMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setName(menuDto.getName());
        menu.setIsUrl(menuDto.getIsUrl());
        menu.setPath(menuDto.getPath());
        menu.setParentId(menuDto.getParentId());
        menu.setSort(menuDto.getSort());
        menu.setIcon(menuDto.getIcon());
        //加入到数据库
        menuRepository.saveAndFlush(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setId(menuDto.getId());
        menu.setName(menuDto.getName());
        menu.setIsUrl(menuDto.getIsUrl());
        menu.setPath(menuDto.getPath());
        menu.setParentId(menuDto.getParentId());
        menu.setSort(menuDto.getSort());
        menu.setIcon(menuDto.getIcon());
        //更新
        menuRepository.save(menu);
    }

    @Override
    public MenuDto findMenuById(Long id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);

        if(!menuOptional.isPresent()){
            //用户不存在，走不存在处理
            return null;
        }
        //domain to dto
        Menu menu = menuOptional.get();
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setIsUrl(menu.getIsUrl());
        menuDto.setPath(menu.getPath());
        menuDto.setParentId(menu.getParentId());
        menuDto.setSort(menu.getSort());
        menuDto.setIcon(menu.getIcon());

        return menuDto;
    }

    @Override
    public List<MenuDto> findAllMenu() {
        List<Menu> menuList =  menuRepository.findAll();
        List<MenuDto> menuDtoList = new ArrayList<>();;
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = (Menu) menuList.get(i);
            //domain to dto
            MenuDto menuDto = new MenuDto();
            menuDto.setId(menu.getId());
            menuDto.setName(menu.getName());
            menuDto.setIsUrl(menu.getIsUrl());
            menuDto.setPath(menu.getPath());
            menuDto.setParentId(menu.getParentId());
            menuDto.setSort(menu.getSort());
            menuDto.setIcon(menu.getIcon());

            menuDtoList.add(menuDto);
        }
        return menuDtoList;
    }
}
