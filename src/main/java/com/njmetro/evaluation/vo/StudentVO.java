package com.njmetro.evaluation.vo;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname StudentVo
 * @Description TODO
 * @Date 2020-09-22 15:35
 */
@Data
public class StudentVO {
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
     * 二维码编号
     */
    private String twoDimensionalCode;

    /**
     * 是否为队长 1: 是队长 0：不是队长
     */
    private String leader;

}
