package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname JudgeReadyShowVO
 * @Description TODO
 * @Date 2020-10-18 10:10
 */
@Data
public class JudgeReadyShowVO {
    /**
     * judgeDrawResult 表中的id
     */
    public Integer id;
    /**
     * 裁判姓名
     */
    public String name;
    /**
     * 裁判状态
     */
    public Integer state;
}
