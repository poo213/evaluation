package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.DrawState;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-10-02
 */
public interface DrawStateService extends IService<DrawState> {
    /**
     * 将 抽签状态表 state 状态设为 false
     *
     * @param id id
     * @return
     */
    Integer setDrawStatusFalse(Integer id);
}
