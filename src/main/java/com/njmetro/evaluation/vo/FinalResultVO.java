package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: evaluation
 * @description: 最终得分
 * @author: zc
 * @create: 2020-10-04 15:48
 **/
@Data
public class FinalResultVO {
    private  Integer id;
    @TableField("student_id")
    private Integer studentId;
    @TableField("company_name")
    private String companyName;
    @TableField("student_code")
    private String studentCode;
    @TableField("id_card")
    private String idCard;
    @TableField("student_name")
    private String studentName;
    /**
     * 实操得分
     */
    private BigDecimal result;
    /**
     * 机考得分
     */
    private BigDecimal computerTestResult;
    /**
     * 综合成绩
     */
    private BigDecimal comprehensiveResult;
}
