package com.njmetro.evaluation.param.seat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname UpdataSeatParam
 * @Description TODO
 * @Date 2020-09-23 10:16
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateSeatParam {
    /**
     * 座位ID
     */
    private Integer id;
    /**
     * 赛位编号
     */
    private String code;

    /**
     * 赛位名称
     */
    private String name;

    /**
     * 赛位类型 光缆接续、视频搭建、交换机组网
     */
    private String type;

    /**
     * 赛位组号 1-6组
     */
    private Integer groupNumber;
}
