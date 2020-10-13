package com.njmetro.evaluation.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.njmetro.evaluation.dto.TestQuestionExcelDTO;
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
@TableName("test_question")
public class TestQuestion implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 考题 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 考题编号
     */
    @TableField("code")
    private String code;

    /**
     * 考题名称
     */
    @TableField("name")
    private String name;

    /**
     * 赛位类型
     */
    @TableField("seat_type")
    private String seatType;

    /**
     * 考题URL
     */
    @TableField("url")
    private String url;

    /**
     * 考试时长
     */
    @TableField("test_time")
    private Integer testTime;

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

    public TestQuestion(){}

    public TestQuestion(TestQuestionExcelDTO testQuestionExcelDTO){
        this.id = testQuestionExcelDTO.getId();
        this.code = testQuestionExcelDTO.getCode();
        this.name = testQuestionExcelDTO.getName();
        this.seatType = testQuestionExcelDTO.getSeatType();
        this.url = testQuestionExcelDTO.getUrl();
        this.testTime = testQuestionExcelDTO.getTestTime();
    }


}
