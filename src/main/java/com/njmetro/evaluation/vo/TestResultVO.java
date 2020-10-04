package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
    @TableField("student_code")
    private String studentCode;
    @TableField("student_name")
    private String studentName;
    @TableField("judge_id")
    private Integer judgeId;
    @TableField("judge_name")
    private String judgeName;
    private Double result;
    private Integer flag;

}
