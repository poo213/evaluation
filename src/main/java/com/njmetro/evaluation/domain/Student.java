package com.njmetro.evaluation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student")
/**
 * 自动忽略不知道的json字段
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 考生 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考生编号
     */
    @TableField("code")
    private String code;

    /**
     * 考生姓名
     */
    @TableField("name")
    @NotBlank(message = "考生姓名不能为空")
    private String name;

    /**
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 考生手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 考生单位名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 二维码编号
     */
    @TableField("two_dimensional_code")
    private String twoDimensionalCode;

    /**
     * 是否为队长 1: 是队长 0：不是队长
     */
    @TableField("leader")
    private Integer leader;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 签到状态
     */
    @TableField("sign_state")
    private String signState;

}
