package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.Admin;
import com.njmetro.evaluation.domain.Menu;
import com.njmetro.evaluation.service.AdminService;
import com.njmetro.evaluation.service.MenuService;
import com.njmetro.evaluation.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-10-27
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    public final AdminService adminService;
    public final MenuService menuService;


    /**
     * 根据用户权限获取 menu列表
     * @return
     */
    @GetMapping("/getMenuByRole")
    List<MenuVO> getMenuByRole(){
        // 获取menuList
        Admin admin = adminService.getById(2);
        String menuIdList = admin.getMenuList();
        String[] menuStrArray = menuIdList.split(",");
        List<Menu> menuList = new ArrayList<>();
        for(int i =0; i< menuStrArray.length ;i++){
            try {
                int menuId = Integer.parseInt(menuStrArray[i]);
                menuList.add(menuService.getById(menuId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // 包一层
        List<MenuVO> menuVOList = new ArrayList<>();
        for(Menu menu : menuList){
            MenuVO menuVO = new MenuVO(menu);
            menuVOList.add(menuVO);
        }
        return menuVOList;
    }



}

