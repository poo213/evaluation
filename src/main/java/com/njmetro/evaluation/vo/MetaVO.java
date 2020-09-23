package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import jdk.jfr.DataAmount;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 牟欢
 * @Classname MetaVO
 * @Description TODO
 * @Date 2020-09-22 16:41
 */
@Data
public class MetaVO {
    /**
     * meta属性 图标
     */
    private String icon;

    /**
     * meta属性 导航名称
     */
    private String title;

    /**
     * 自定义构造函数
     * @param icon  icon
     * @param title title
     */
    public MetaVO(String icon, String title) {
        this.icon = StringUtils.isBlank(icon)?null:icon;
        this.title = title;
    }
}
