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
@TableName("pause_record")
public class PauseRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * seat_draw_id id
     */
    @TableField("seat_draw_id")
    private Integer seatDrawId;

    /**
     * 类型 0 :暂停  1：开始
     */
    @TableField("type")
    private Integer type;

    /**
     * 场次
     */
    @TableField("game_number")
    private Integer gameNumber;
    /**
     * 轮次
     */
    @TableField("game_round")
    private Integer gameRound;
    /**
     * 学生id
     */
    @TableField("student_id")
    private Integer studentId;
    /**
     * 暂停用时
     */
    @TableField("pause_time")
    private Integer pauseTime;
    /**
     * 是否有效标记
     */
    @TableField("flag")
    private Boolean flag;
    /**
     * 是否有效标记
     */
    @TableField("change_people")
    private String changePeople;
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
