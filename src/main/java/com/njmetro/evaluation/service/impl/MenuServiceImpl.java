package com.njmetro.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.Menu;
import com.njmetro.evaluation.mapper.MenuMapper;
import com.njmetro.evaluation.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.MenuVO;
import com.njmetro.evaluation.vo.MetaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<MenuVO> getMenuList() {
        List<MenuVO> menuVOList = new ArrayList<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu>  menuList = menuMapper.selectList(queryWrapper);
        for(Menu menu : menuList){
            MenuVO menuVO = new MenuVO(menu);
            menuVOList.add(menuVO);
        }
        return menuVOList;
    }
}
