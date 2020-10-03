package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
public interface SeatDrawService extends IService<SeatDraw> {

    /**
     * 重置 seat_draw 表格
     *
     * @return true
     */
    Boolean deleteTable();

}
