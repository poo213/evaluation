package com.njmetro.evaluation.vo;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @Classname DetailResultVO
 * @Description TODO
 * @Date 2020/11/16 18:47
 * @Created by zc
 */
@Data
public class DetailResultVO {
    private Integer id;
    private String code;
    private String name;
    private String companyName;
    private String idCard;
    private BigDecimal one;
    private BigDecimal two;
    private BigDecimal three;
    private BigDecimal all;
}
