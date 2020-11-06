package com.njmetro.evaluation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Admin;
import com.njmetro.evaluation.domain.Company;
import com.njmetro.evaluation.domain.Menu;
import com.njmetro.evaluation.exception.AdminException;
import com.njmetro.evaluation.param.login.LoginParam;
import com.njmetro.evaluation.service.AdminService;
import com.njmetro.evaluation.service.MenuService;
import com.njmetro.evaluation.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 用户登录
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    String login(@Valid @RequestBody LoginParam loginParam){
        log.info("loginParam {}",loginParam);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",loginParam.getUsername())
                .eq("password",loginParam.getPassword());
        Admin admin = adminService.getOne(queryWrapper);
        if(admin == null){
            throw new AdminException("用户名或密码错误");
        }else {
            // 返回 用户adminId
            return admin.getId().toString();
        }
    }

    /**
     * 根据用户权限获取 menu列表
     * @return
     */
    @GetMapping("/getMenuByRole")
    List<MenuVO> getMenuByRole(@RequestAttribute("adminId") Integer adminId){
        // 获取menuList
        Admin admin = adminService.getById(adminId);
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

