package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Menu;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author 牟欢
 * @Classname MenuVO
 * @Description TODO
 * @Date 2020-09-22 16:41
 */
@Data
public class MenuVO {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 上级菜单编号，无上级菜单为 0
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 重定向路径
     */
    private String redirect;

    /**
     * 组件
     */
    private String component;

    /**
     * meta 信息
     */
    private MetaVO meta;

    /**
     * 自定义构造函数
     * @param menu menu
     */
    public MenuVO(Menu menu) {
        this.id = menu.getId();
        this.component = menu.getComponent();
        this.name = menu.getName();
        this.parentId = menu.getParentId();
        this.path = menu.getPath();
        this.redirect = menu.getRedirect();
        this.meta = new MetaVO(menu.getIcon(),menu.getTitle());
    }
}
