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
 * @since 2020-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("seat_draw")
public class SeatDraw implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单位编码
     */
    @TableField("company_id")
    private Integer companyId;

    /**
     * 考生编码
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 赛位号id
     */
    @TableField("seat_id")
    private Integer seatId;

    /**
     * 抽签人姓名
     */
    @TableField("draw_name")
    private String drawName;

    /**
     * 抽签时间
     */
    @TableField("draw_time")
    private LocalDateTime drawTime;

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
     * 组名（A-F）
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 状态，1表示登录（正在考试），2比赛中断，3考生就绪，4表示考试结束
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


}
