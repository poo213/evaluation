package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.vo.JudgeReadyShowVO;

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
    /**
     * 根据裁判座位Id获取裁判就绪状态
     * @param seatId 座位id
     * @return
     */
    JudgeReadyShowVO getJudgeReadyShowVO(Integer seatId);

}
