package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.MenuVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-22
 */

public interface MenuService extends IService<Menu> {
    /**
     * 获取全部目录
     *
     * @return
     */
    List<MenuVO> getMenuList();
}
