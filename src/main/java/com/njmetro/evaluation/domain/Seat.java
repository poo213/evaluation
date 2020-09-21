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
@TableName("seat")
public class Seat implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 赛位 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 赛位编号
     */
    @TableField("code")
    private String code;

    /**
     * 赛位名称
     */
    @TableField("name")
    private String name;

    /**
     * 赛位类型 光缆接续、视频搭建、交换机组网
     */
    @TableField("type")
    private String type;

    /**
     * 赛位组号 1-6组
     */
    @TableField("group_number")
    private Integer groupNumber;

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
