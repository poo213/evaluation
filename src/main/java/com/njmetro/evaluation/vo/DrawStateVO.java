package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.njmetro.evaluation.domain.DrawState;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname DrawStateVO
 * @Description TODO
 * @Date 2020-10-02 13:39
 */
@Data
public class DrawStateVO {
    /**
     * id
     */
    private Integer id;

    /**
     * 抽签名称
     */
    private String drawName;

    /**
     * 抽签状态 true: 允许抽签   false: 不允许抽签
     */
    private String stateName;

    public DrawStateVO(DrawState drawState){
        this.id = drawState.getId();
        this.drawName = drawState.getDrawName();
        if (drawState.getState()){
            this.stateName = "可以抽签";
        }else {
            this.stateName = "不可以抽签";
        }

    }

}
