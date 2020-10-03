
package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.SeatDraw;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.vo.SeatDrawVO;

import java.util.List;

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

    List<SeatDrawVO> getSeatDraw();
}