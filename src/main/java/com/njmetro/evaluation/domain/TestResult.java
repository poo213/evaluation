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
 * @since 2020-09-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_result")
public class TestResult implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考生id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 裁判id
     */
    @TableField("judge_id")
    private Integer judgeId;

    /**
     * 题目id
     */
    @TableField("question_id")
    private Integer questionId;

    /**
     * 评分标准id
     */
    @TableField("question_standard_id")
    private Integer questionStandardId;

    /**
     * 得分
     */
    @TableField("cent")
    private Integer cent;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间，当变为4的时候，此时间表示考试结束时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
