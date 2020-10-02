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
    List<SeatDrawVO> getSeatDraw();
}
