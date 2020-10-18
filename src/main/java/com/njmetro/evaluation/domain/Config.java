package com.njmetro.evaluation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("config")
public class Config implements Serializable {

    private static final long serialVersionUID=1L;

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
     * 0: 初始化值，1： 考生裁判全部就绪，可以抽题 2： 可以下发试题 3： 试题下发完成 4：本轮结束，可以开始下一轮
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
