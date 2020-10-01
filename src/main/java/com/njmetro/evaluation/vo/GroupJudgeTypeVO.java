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
public class GroupJudgeTypeVO {
    public String typeName;
    List<GroupTypeJudgeVO> groupTypeJudgeVOList;
}
