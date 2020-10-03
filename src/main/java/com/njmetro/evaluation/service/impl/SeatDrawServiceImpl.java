package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.mapper.DrawStateMapper;
import com.njmetro.evaluation.mapper.SeatDrawMapper;
import com.njmetro.evaluation.service.SeatDrawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-28
 */
@Service
public class SeatDrawServiceImpl extends ServiceImpl<SeatDrawMapper, SeatDraw> implements SeatDrawService {
    @Autowired
    SeatDrawMapper seatDrawMapper;
    @Autowired
    DrawStateMapper drawStateMapper;

    @Override
    public Boolean deleteTable() {
        seatDrawMapper.delete();
        // 重置 抽签状态
        DrawState drawState2 = drawStateMapper.selectById(2);
        drawState2.setState(true);
        drawStateMapper.updateById(drawState2);
        return true;
    }
}
