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
@TableName("test_question_standard")
public class TestQuestionStandard implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 评分标准 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评分标准所在考题 id
     */
    @TableField("test_question_id")
    private Integer testQuestionId;

    /**
     * 考核内容
     */
    @TableField("text")
    private String text;

    /**
     * 考核点
     */
    @TableField("point")
    private String point;

    /**
     * 分值
     */
    @TableField("score")
    private Integer score;

    /**
     * 评分标准
     */
    @TableField("standard")
    private String standard;

    /**
     * 评分步长
     */
    @TableField("step")
    private Integer step;

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
