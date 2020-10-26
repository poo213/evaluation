package com.njmetro.evaluation.vo;/**
 * @Classname TestCompanyResultVO
 * @Description TODO
 * @Date 2020-10-26 14:16
 * @Created by zc
 */

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: evaluation
 * @description: 队伍成绩
 * @author: zc
 * @create: 2020-10-26 14:16
 **/
@Data
public class TestCompanyResultVO {
    private Integer id;
    private String companyName;
    /**
     * 综合成绩
     */
    @TableField("comprehensive_result")
    private BigDecimal comprehensiveResult;

}
