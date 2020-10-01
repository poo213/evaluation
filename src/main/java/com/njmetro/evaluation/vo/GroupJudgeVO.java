package com.njmetro.evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname GroupJudgeVO
 * @Description TODO
 * @Date 2020-10-01 15:11
 */
@Data
public class GroupJudgeVO {
    public String groupName;
    public List<GroupJudgeTypeVO> groupJudgeTypeVOList;
}
