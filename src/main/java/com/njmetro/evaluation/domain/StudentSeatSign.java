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
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student_seat_sign")
public class StudentSeatSign implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 人员编号id
     */
    @TableField("student_id")
    private Integer studentId;

    /**
     * 赛位号id
     */
    @TableField("seat_id")
    private Integer seatId;

    /**
     * 比赛场次
     */
    @TableField("game_number")
    private Integer gameNumber;

    /**
     * 报道状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 考生签到时间
     */
    @TableField("sign_time")
    private LocalDateTime signTime;

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
