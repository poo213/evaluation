package com.njmetro.evaluation.param.pad;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname UpdatePad
 * @Description TODO
 * @Date 2020-09-23 15:37
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatePadParam {
    /**
     * 平板 自增ID
     */
    private Integer id;

    /**
     * 平板编号
     */
    private String code;

    /**
     * 平板绑定的ip地址
     */
    private String ip;

    /**
     * 对应工位id
     */
    private Integer seatId;

    /**
     * 平板用途（1: 考生、2:评委、3:主裁）
     */
    private Integer type;

}
