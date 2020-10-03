package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.mapper.SeatDrawMapper;
import com.njmetro.evaluation.service.SeatDrawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.SeatDrawVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
@Service
@RequiredArgsConstructor
public class SeatDrawServiceImpl extends ServiceImpl<SeatDrawMapper, SeatDraw> implements SeatDrawService {
    private final SeatDrawMapper seatDrawMapper;

    @Override
    public List<SeatDrawVO> getSeatDraw() {
        return seatDrawMapper.getSeatDraw();
    }
}