package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
public interface JudgeDrawResultService extends IService<JudgeDrawResult> {

    /**
     * 重置选择12个执行裁判的结果
     *
     * @return
     */
    Boolean resetJudgeDrawResult();

}
