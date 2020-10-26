package com.njmetro.evaluation.vo;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname StudentReadyShowVO
 * @Description TODO
 * @Date 2020-10-18 9:38
 */
@Data
public class StudentReadyShowVO {
    /**
     * seatDraw表中的id
     */
    public Integer id;
    /**
     * 学生姓名
     */
    public String name;

    /**
     * 学生编号
     */
    public String code;
    /**
     * 学生状态
     */
    public Integer state;

}
