package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname StudentStateVO
 * @Description TODO
 * @Date 2020-10-09 16:16
 */
@Data
public class StudentStateVO {
    public Integer state;
    public Integer remainingTime;

    public StudentStateVO(Integer state, Integer remainingTime) {
        this.state = state;
        this.remainingTime = remainingTime;
    }
}
