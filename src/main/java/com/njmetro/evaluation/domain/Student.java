package com.njmetro.evaluation.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
    @Excel(name="编码")
    private String code;

    /**
     * 考生姓名
     */
    @TableField("name")
    @NotBlank(message = "考生姓名不能为空")
    @Excel(name="姓名")
    private String name;

    /**
     * 身份证号
     */
    @TableField("id_card")
    @Excel(name="身份证号")
    private String idCard;

    /**
     * 性别
     */
    @TableField("sex")
    @Excel(name="性别")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    @Excel(name="年龄")
    private Integer age;

    /**
     * 考生手机号
     */
    @TableField("phone")
    @Excel(name="手机号码")
    private String phone;

    /**
     * 考生单位名称
     */
    @TableField("company_name")
    @Excel(name="公司")
    private String companyName;

    /**
     * 二维码编号
     */
    @TableField("two_dimensional_code")
    @Excel(name="二维码")
    private String twoDimensionalCode;

    /**
     * 是否为队长 1: 是队长 0：不是队长
     */
    @TableField("leader")
    @Excel(name="是否领队")
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
    /**
     * 考试当天签到状态
     */
    @TableField("test_day_state")
    private Integer testDayState;
    /**
     * 机考成绩
     */
    @TableField("computer_test_result")
    private Double computerTestResult;
}
