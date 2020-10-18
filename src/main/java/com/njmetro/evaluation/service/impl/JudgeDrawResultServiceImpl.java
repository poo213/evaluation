package com.njmetro.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.domain.JudgeDrawResult;
import com.njmetro.evaluation.mapper.DrawStateMapper;
import com.njmetro.evaluation.mapper.JudgeDrawResultMapper;
import com.njmetro.evaluation.service.JudgeDrawResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.JudgeReadyShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-29
 */
@Service
public class JudgeDrawResultServiceImpl extends ServiceImpl<JudgeDrawResultMapper, JudgeDrawResult> implements JudgeDrawResultService {

    @Autowired
    JudgeDrawResultMapper judgeDrawResultMapper;
    @Autowired
    DrawStateMapper drawStateMapper;

    @Override
    public Boolean resetJudgeDrawResult() {
        QueryWrapper<JudgeDrawResult> judgeDrawResultQueryWrapper = new QueryWrapper<>();
        List<JudgeDrawResult> judgeDrawResultList = judgeDrawResultMapper.selectList(judgeDrawResultQueryWrapper);
        for(JudgeDrawResult judgeDrawResult : judgeDrawResultList){
            // 重置裁判 抽签结果
            judgeDrawResult.setJudgeId(0);
            judgeDrawResultMapper.updateById(judgeDrawResult);
            DrawState drawState5 = drawStateMapper.selectById(5);
            drawState5.setState(true);
            drawStateMapper.updateById(drawState5);
        }
        return true;
    }

    @Override
    public JudgeReadyShowVO getJudgeReadyShowVO(Integer seatId) {
        return judgeDrawResultMapper.selectOneBySeatId(seatId);
    }
}
