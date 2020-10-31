package com.njmetro.evaluation.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("judge")
public class Judge implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 评委 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评委编号
     */
    @TableField("code")
    @Excel(name="编码")
    private String code;

    /**
     * 评委姓名
     */
    @TableField("name")
    @Excel(name="姓名")
    private String name;

    /**
     * 评委身份证号
     */
    @TableField("id_card")
    @Excel(name="身份证号")
    private String idCard;

    /**
     * 性别 0：男 1：女
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
     * 评委手机号
     */
    @TableField("phone")
    @Excel(name="手机")
    private String phone;

    /**
     * 评委所在单位名称
     */
    @TableField("company_name")
    @Excel(name="公司")
    private String companyName;

    /**
     * 评委所在单位Id
     */
    @TableField("company_id")
    @Excel(name="公司ID")
    private Integer companyId;

    /**
     * 二维码编号
     */
    @TableField("two_dimensional_code")
    @Excel(name="二维码")
    private String twoDimensionalCode;

    /**
     * 签到1 未签到0
     */
    @TableField("sign_state")
    private String signState;

    /**
     * 监考类型（抽签决定）
     */
    @TableField("judge_type")
    private String judgeType;

    /**
     * 一般裁判1，备用裁判0
     */
    @TableField("master")
    private Integer master;

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


}
