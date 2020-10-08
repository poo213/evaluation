package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @program: evaluation
 * @description: 签到VO
 * @author: zc
 * @create: 2020-10-08 13:09
 **/
@Data
public class SignVO {
    /**
     * 考生 自增ID
     */
    private Integer id;

    /**
     * 考生编号
     */
    private String code;

    /**
     * 考生姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别 0：男 1：女
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 考生手机号
     */
    private String phone;

    /**
     * 考生单位名称
     */
    private String companyName;

    /**
     * 签到类型
     */
    private Integer type;

}