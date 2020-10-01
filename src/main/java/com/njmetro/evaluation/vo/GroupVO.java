package com.njmetro.evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 牟欢
 * @Classname GroupVO
 * @Description TODO
 * @Date 2020-09-30 16:06
 */
@Data
public class GroupVO {
    /**
     * 分组名称
     */
    public String groupName;

    /**
     * 三个实体类型
     */
     public List<GroupTypeVO> groupTypeVOList;
}
