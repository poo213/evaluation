package com.njmetro.evaluation.domain;

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
 * @since 2020-09-21
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
    private String code;

    /**
     * 评委姓名
     */
    @TableField("name")
    private String name;

    /**
     * 评委身份证号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 性别 0：男 1：女
     */
    @TableField("sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 评委手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 评委所在单位名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 二维码编号
     */
    @TableField("two_dimensional_code")
    private String twoDimensionalCode;

    /**
     * 是否为主裁判 1: 是主裁判 0：不是主裁判
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

    /**
     * 签到状态
     */
    @TableField("sign_state")
    private String signState;


}
