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
 * @since 2020-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test_result")
public class TestResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 比赛场次（1-7）
     */
    @TableField("game_number")
    private Integer gameNumber;

    /**
     * 比赛轮次（1，2，3）
     */
    @TableField("game_round")
    private Integer gameRound;
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
    private double cent;

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

    @TableField("state")
    private Integer state;

    public TestResult() {
    }

    public TestResult(Integer gameNumber, Integer gameRound, Integer studentId, Integer judgeId, Integer questionId) {
        this.gameNumber = gameNumber;
        this.gameRound = gameRound;
        this.studentId = studentId;
        this.judgeId = judgeId;
        this.questionId = questionId;
    }
}
