package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @program: evaluation
 * @description: 打分结果详情
 * @author: zc
 * @create: 2020-10-16 15:50
 **/
@Data
public class TestResultDetailVO {
    private  Integer id;
    @TableField("student_id")
    private Integer studentId;
    @TableField("student_code")
    private String studentCode;
    @TableField("student_name")
    private String studentName;
    @TableField("judge_id")
    private Integer judgeId;
    @TableField("question_id")
    private String questionId;
    @TableField("question_name")
    private String questionName;
    @TableField("question_standard_id")
    private String questionStandardId;
    @TableField("question_standard_name")
    private String questionStandardName;
    @TableField("judge_name")
    private String judgeName;
    private Double cent;
}
