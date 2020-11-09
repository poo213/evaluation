package com.njmetro.evaluation.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @program: evaluation
 * @description: 计算机成绩导入
 * @author: zc
 * @create: 2020-10-10 11:27
 **/
@Data
public class ComputerTestResultExcelDTO {
    @Excel(name="身份证号")
    private String idCard;
    @Excel(name="成绩")
    private Double cent;
}
