package com.njmetro.evaluation.vo;

import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import lombok.Data;

/**
 * @author 牟欢
 * @Classname JudgeDrawVO
 * @Description TODO
 * @Date 2020-10-01 14:42
 */
@Data
public class JudgeDrawVO {
    public String name;
    public String code;
    public String masterName;

    public JudgeDrawVO(Judge judge){
        this.name = judge.getName();
        this.code = judge.getCode();
        if(judge.getMaster() == 0){
            masterName = "一般裁判";
        }else {
            masterName = "执行裁判";
        }

    }
}
