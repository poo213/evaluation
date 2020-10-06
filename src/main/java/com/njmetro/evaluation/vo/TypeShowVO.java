package com.njmetro.evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname GroupJudgeTypeVO
 * @Description TODO
 * @Date 2020-10-01 15:18
 */
@Data
public class TypeShowVO {
    /**
     * 考试类型
     */
    public String typeName;
    /**
     * 考生基础信息
     */
    StudentShowVO studentShowVO;
    /**
     * 两个裁判的基础信息
     */
    List<JudgeShowVO> judgeShowVOList;

    /**
     * 自定义构造函数
     *
     * @param typeName        考试类型
     * @param studentShowVO   考生基础信息
     * @param judgeShowVOList 两个裁判的基础信息
     */
    public TypeShowVO(String typeName, StudentShowVO studentShowVO, List<JudgeShowVO> judgeShowVOList) {
        this.typeName = typeName;
        this.studentShowVO = studentShowVO;
        this.judgeShowVOList = judgeShowVOList;
    }

    public TypeShowVO(){

    }
}
