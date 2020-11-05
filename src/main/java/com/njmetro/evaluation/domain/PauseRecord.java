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
     * 类型 0 :暂停  1：开始
     */
    @TableField("game_number")
    private Integer gameNumber;

    @TableField("game_round")
    private Integer gameRound;

    @TableField("student_id")
    private Integer studentId;

    @TableField("pause_time")
    private Integer pauseTime;

    @TableField("flag")
    private Boolean flag;
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
