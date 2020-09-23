package com.njmetro.evaluation.param.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 牟欢
 * @Classname UpdateStudentParam
 * @Description TODO
 * @Date 2020-09-22 15:05
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateStudentParam {
    @NotNull(message = "ID 不能为空")
    private Integer id;
    /**
     * 考生编号
     */
    @NotBlank(message = "考生编码不能为空")
    private String code;

    /**
     * 考生姓名
     */
    @NotBlank(message = "考生姓名不能为空")
    private String name;

    /**
     * 身份证号
     */
    @NotBlank(message = "考生身份证不能为空")
    private String idCard;

    /**
     * 性别 0：男 1：女
     */
    @NotNull(message = "考生性别不能为空")
    private Integer sex;

    /**
     * 年龄
     */
    @NotNull(message = "考生年龄不能为空")
    private Integer age;

    /**
     * 考生手机号
     */
    @NotBlank(message = "考生手机号码不能为空")
    private String phone;

    /**
     * 考生单位名称
     */
    @NotBlank(message = "考生单位名称不能为空")
    private String companyName;

    /**
     * 二维码编号
     */
    private String twoDimensionalCode;

    /**
     * 是否为队长 1: 是队长 0：不是队长
     */
    @NotNull(message = "用户是否为领导不能为空")
    private Integer leader;
}
