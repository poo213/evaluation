package com.njmetro.evaluation.param.pad;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname SavePadParam
 * @Description TODO
 * @Date 2020-09-23 15:36
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavePadParam {
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
