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
public class GroupShowVO {
    /**
     * 群组名称
     */
    public String groupName;
    /**
     * 三种考试类型列表
     */
    public List<TypeShowVO> typeShowVOList;

    /**
     * 自定义构造函数
     *
     * @param groupName      群组名称
     * @param TypeShowVOList 三种考试类型列表
     */
    public GroupShowVO(String groupName, List<TypeShowVO> TypeShowVOList) {
        this.groupName = groupName;
        this.typeShowVOList = TypeShowVOList;
    }

    public GroupShowVO() {
    }
}


