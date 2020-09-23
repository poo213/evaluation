package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.service.MenuService;
import com.njmetro.evaluation.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/getMenu")
    public List<MenuVO> getMenu() {
       return menuService.getMenuList();

    }

}

