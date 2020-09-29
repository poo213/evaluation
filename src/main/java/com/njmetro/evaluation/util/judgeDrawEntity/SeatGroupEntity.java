package com.njmetro.evaluation.util.judgeDrawEntity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mubaisama
 */
@Data
@Component
public class SeatGroupEntity {
    /**
     * 赛位所在 分组 A-F
     */
    public String groupName;

    /**
     *  赛组 中抽签选中的 参赛队列表（一共是7个代表队）
     */
    public List<Integer> companyDrawnList;

    /**
     * 选中裁判存放的list  每种类型 一共有 2 个位置
     */
    public List<SaveJudgeEntity> saveJudgeEntityList;

    /**
     * 判断 赛组 是否占满裁判
     */
    public Boolean isFull;

    /**
     * 赛组 ID
     */
    public Integer groupId;

    /**
     *  赛组当前抽签类型
     */
    public String typeName;
}
