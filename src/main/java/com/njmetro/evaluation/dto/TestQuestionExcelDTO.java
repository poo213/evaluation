package com.njmetro.evaluation.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname TestQuestionExcelDTO
 * @Description TODO
 * @Date 2020-10-12 14:40
 */
@Data
public class TestQuestionExcelDTO {
    /**
     * 考题 自增ID
     */
    @Excel(name = "试题序号")
    private Integer id;

    /**
     * 考题编号
     */
    @Excel(name = "试题编号")
    private String code;

    /**
     * 考题名称
     */
    @Excel(name = "试题名称")
    private String name;

    /**
     * 赛位类型
     */
    @Excel(name = "试题类型")
    private String seatType;

    /**
     * 考题URL
     */
    @Excel(name = "试题文件名称")
    private String url;

    /**
     * 考试时长
     */
    @Excel(name = "考试时长")
    private Integer testTime;
}
