package com.njmetro.evaluation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
    @TableField("student_code")
    private String studentCode;
    @TableField("student_name")
    private String studentName;
    private Double result;
}
