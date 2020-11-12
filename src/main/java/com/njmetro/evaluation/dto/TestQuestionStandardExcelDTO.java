package com.njmetro.evaluation.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname TestQuestionStandardDTO
 * @Description TODO
 * @Date 2020-10-12 10:55
 */
@Data
public class TestQuestionStandardExcelDTO {

    /**
     * 评分标准所在考题 id
     */
    @Excel(name = "考题id")
    private Integer testQuestionId;

    /**
     * 考核内容
     */
    @Excel(name = "考核内容")
    private String text;

    /**
     * 考核点
     */
    @Excel(name = "考核点")
    private String point;

    /**
     * 分值
     */
    @Excel(name = "最小分值")
    private Double minScore;

    /**
     * 分值
     */
    @Excel(name = "最大分值")
    private Double score;

    /**
     * 评分标准
     */
    @Excel(name = "评分标准")
    private String standard;

    /**
     * 评分标准
     */
    @Excel(name = "步长")
    private Double step;

}
