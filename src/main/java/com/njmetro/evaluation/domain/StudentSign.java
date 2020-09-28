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
@TableName("student_sign")
public class StudentSign implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 签到表自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考生所在单位名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 考生id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 考生签到时间
     */
    @TableField("sign_time")
    private LocalDateTime signTime;

    /**
     * 考生签到状态 1： 已签到 0： 未签到
     */
    @TableField("state")
    private Integer state;

    /**
     * 登记人姓名
     */
    @TableField("register_name")
    private String registerName;


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
