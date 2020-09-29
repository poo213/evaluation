package com.njmetro.evaluation.param.judge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname SaveJudgeParam
 * @Description TODO
 * @Date 2020-09-23 15:51
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveJudgeParam {
    /**
     * 评委编号
     */
    private String code;

    /**
     * 评委姓名
     */
    private String name;

    /**
     * 评委身份证号
     */
    private String idCard;

    /**
     * 性别 0：男 1：女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 评委手机号
     */
    private String phone;

    /**
     * 评委所在单位名称
     */
    private String companyName;

    /**
     * 二维码编号
     */
    private String twoDimensionalCode;

    /**
     * 是否为主裁判 1: 是主裁判 0：不是主裁判
     */
    private Integer master;
}
