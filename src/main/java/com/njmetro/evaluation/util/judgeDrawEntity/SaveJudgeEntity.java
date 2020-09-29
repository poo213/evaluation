package com.njmetro.evaluation.util.judgeDrawEntity;

import lombok.Data;

/**
 * @author 牟欢
 * @Classname SaveJudgeEntity
 * @Description TODO
 * @Date 2020-09-24 10:53
 */
@Data
public class SaveJudgeEntity {
    /**
     * 裁判所在单位ID
     */
    public Integer companyId;

    /**
     * 裁判ID
     */
    public Integer judgeId;

    public Integer seatGroupIndex;

    SaveJudgeEntity() {
    }

    public SaveJudgeEntity(Integer companyId, Integer judgeId) {
        this.companyId = companyId;
        this.judgeId = judgeId;
    }

    public SaveJudgeEntity(JudgeEntity judgeEntity) {
        this.companyId = judgeEntity.getCompanyId();
        this.judgeId = judgeEntity.getJudgeId();
    }


}
