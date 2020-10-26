package com.njmetro.evaluation.domain;

import java.math.BigDecimal;
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
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_final_result")
public class TestFinalResult implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户自增Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 学生姓名
     */
    @TableField("student_name")
    private String studentName;

    /**
     * 学生编码
     */
    @TableField("student_code")
    private String studentCode;

    /**
     * 公司名
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 实操得分
     */
    @TableField("result")
    private BigDecimal result;

    /**
     * 机考得分
     */
    @TableField("computer_test_result")
    private BigDecimal computerTestResult;

    /**
     * 综合得分
     */
    @TableField("comprehensive_result")
    private BigDecimal comprehensiveResult;

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
