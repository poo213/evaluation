package com.njmetro.evaluation.vo.api;

import lombok.Data;

/**
 * @program: evaluation
 * @description: 对外API需要用
 * @author: zc
 * @create: 2020-09-29 09:18
 **/
@Data
public class StudentInfo {
    /**
     * 考生姓名
     */
    private String name;
    /**
     * 考生编号
     */
    private String code;
    /**
     * 考生单位
     */
    private String companyName;
    /**
     * 考生单位
     */
    private String idCard;
    /**
     * 场次
     */
    private Integer gameNumber;
    /**
     * 轮次
     */
    private Integer gameRound;
}
