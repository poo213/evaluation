package com.njmetro.evaluation.vo.api;

import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname TestQuestionStandardResultVO
 * @Description TODO
 * @Date 2020-10-08 13:32
 */
@Data
public class TestQuestionStandardResultVO {
    /**
     * 读题时间
     */
    private Integer readTime;
    /**
     * 考试时间
     */
    private Integer testTime;
    /**
     * 比赛名称
     */
    private String testName;

    /**
     * 比赛名称student
     */
    private Integer questionId;
    /**
     * 裁判Id
     */
    private Integer judgeId;
    /**
     * 考生id
     */
    private Integer studentId;
    /**
     * 评分标准
     */
    public List<TestQuestionStandardVO> testQuestionStandardVOList;

    public TestQuestionStandardResultVO(List<TestQuestionStandardVO> testQuestionStandardVOList,String testName,Integer questionId,Integer judgeId,Integer studentId,Integer readTime,Integer testTime){
        this.readTime = readTime;
        this.testTime = testTime;
        this.testName = testName;
        this.questionId = questionId;
        this.judgeId = judgeId;
        this.studentId = studentId;
        this.testQuestionStandardVOList = testQuestionStandardVOList;
    }
}
