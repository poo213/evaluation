package com.njmetro.evaluation.vo.api;

import com.njmetro.evaluation.domain.Judge;
import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname JudgeInformationVO
 * @Description TODO
 * @Date 2020-09-29 10:06
 */
@Data
public class JudgeInformationVO {
    public Judge judge;
    public Integer seatId;
    public Integer gameNumber;
    public Integer gameRound;
}
