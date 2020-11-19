package com.njmetro.evaluation.dto;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @Classname DetailTempDTO
 * @Description TODO
 * @Date 2020/11/16 19:15
 * @Created by zc
 */
@Data
public class DetailTempDTO {
    private Integer gameNumber;
    private Integer gameRound;
    private Integer judgeId;
    private String judgeName;
    private Integer studentId;
    private String studentName;
    private Integer questionId;
    private BigDecimal cent;

    private BigDecimal TimeCent;

    private BigDecimal allCent;
}
