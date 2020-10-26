package com.njmetro.evaluation.vo;/**
 * @Classname TestResultDetailByJudgeIdVO
 * @Description TODO
 * @Date 2020-10-26 10:17
 * @Created by zc
 */

import lombok.Data;

import java.util.List;

/**
 * @program: evaluation
 * @description: 根据指定场次、轮次、学生id、裁判id获取的记录
 * @author: zc
 * @create: 2020-10-26 10:17
 **/
@Data
public class TestResultDetailByJudgeIdVO {
    List<TestResultDetailVO> testResultDetailVOListOne;
    List<TestResultDetailVO> testResultDetailVOListTwo;
}
