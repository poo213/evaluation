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
@TableName("judge_draw_result")
public class JudgeDrawResult implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 赛位id
     */
    @TableField("seat_id")
    private Integer seatId;

    /**
     * 评委id
     */
    @TableField("judge_id")
    private Integer judgeId;

    /**
     * 平板id
     */
    @TableField("pad_id")
    private Integer padId;

    /**
     * 所在赛组id
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * 裁判就绪状态，初始值 0 ，准备就绪：1
     */
    @TableField("state")
    private Integer state;

    /**
     * 监考类型 光缆接续 交换机组网 视频搭建
     */
    @TableField("type_name")
    private String typeName;

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
