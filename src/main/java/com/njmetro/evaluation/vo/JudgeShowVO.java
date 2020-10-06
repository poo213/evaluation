package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname GroupTypeJudgeVO
 * @Description TODO
 * @Date 2020-10-01 12:16
 */
@Data
public class JudgeShowVO {
    /**
     * 裁判编号
     */
    private String code;
    /**
     * 裁判姓名
     */
    private String name;
    /**
     * 裁判就绪状态
     */
    private Boolean state;

    /**
     * 自定义构造函数
     *
     * @param code  裁判编号
     * @param name  裁判姓名
     * @param state 裁判就绪状态
     */
    public JudgeShowVO(String code, String name, Boolean state) {
        this.code = code;
        this.name = name;
        this.state = state;
    }

    public JudgeShowVO() {
    }
}
