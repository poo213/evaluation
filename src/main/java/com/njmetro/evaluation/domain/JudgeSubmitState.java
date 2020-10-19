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
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("judge_submit_state")
public class JudgeSubmitState implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 比赛场次
     */
    @TableField("game_number")
    private Integer gameNumber;

    /**
     * 比赛轮次
     */
    @TableField("game_round")
    private Integer gameRound;

    /**
     * 考生Id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 对应监考裁判Id
     */
    @TableField("judge_id")
    private Integer judgeId;

    /**
     * 裁判是否上报成功 0： 未上报成功 1： 上报成功
     */
    @TableField("state")
    private Integer state;

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

    public JudgeSubmitState() {
    }

    public JudgeSubmitState(SeatDraw seatDraw, Integer judgeId) {
        this.gameNumber = seatDraw.getGameNumber();
        this.gameRound = seatDraw.getGameRound();
        this.studentId = seatDraw.getStudentId();
        this.judgeId = judgeId;
        this.state = 0;
    }


}
