package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.domain.SeatDraw;
import com.njmetro.evaluation.domain.Student;
import com.njmetro.evaluation.mapper.DrawStateMapper;
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
    private final DrawStateMapper drawStateMapper;

    @Override
    public Boolean deleteTable() {
        seatDrawMapper.delete();
        // 重置 抽签状态
        DrawState drawState2 = drawStateMapper.selectById(2);
        drawState2.setState(true);
        drawStateMapper.updateById(drawState2);
        return true;
    }

    @Override
    public List<SeatDrawVO> getSeatDraw() {
        return seatDrawMapper.getSeatDraw();
    }

    @Override
    public void cleanAll() {
        seatDrawMapper.cleanAll();
    }

    @Override
    public List<Student> getStudentShowBySeatId(Integer gameNumber, Integer gameRound, Integer seatId) {
        return seatDrawMapper.selectShowStudentBySeatId(gameNumber,gameRound,seatId);
    }
}