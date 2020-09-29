package com.njmetro.evaluation.util.judgeDrawEntity;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mubaisama
 */
@Data
@Component
public class JudgeEntity {
    /**
     * 裁判所在单位ID
     */
    public Integer companyId;

    /**
     * 裁判ID
     */
    public Integer judgeId;

    /**
     * 裁判与赛组不冲突，但是裁判名额已满，存放起来，方便以后裁判调换位置
     */
    public List<SeatGroupEntity> noConflictSeatGroupEntityList;

}
