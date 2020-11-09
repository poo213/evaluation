package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: evaluation
 * @description: 考生打分情况
 * @author: zc
 * @create: 2020-10-03 14:10
 **/
@Data
public class TestResultVO {
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
    @TableField("judge_id")
    private Integer judgeId;
    @TableField("judge_name")
    private String judgeName;
    private BigDecimal result;
    private BigDecimal timeCent;
    private Integer flag;

}
