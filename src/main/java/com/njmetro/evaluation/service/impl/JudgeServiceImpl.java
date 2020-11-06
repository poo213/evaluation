package com.njmetro.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.mapper.DrawStateMapper;
import com.njmetro.evaluation.mapper.JudgeMapper;
import com.njmetro.evaluation.service.JudgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.JudgeDrawVO;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-27
 */
@Service
public class JudgeServiceImpl extends ServiceImpl<JudgeMapper, Judge> implements JudgeService {
    @Autowired
    JudgeMapper judgeMapper;
    @Autowired
    DrawStateMapper drawStateMapper;

    @Override
    public List<JudgeInfoDTO> getJudgeInfo(String ip) {
        return judgeMapper.getInfo(ip);
    }

    @Override
    public List<JudgeDrawVO> getJudgeDrawVOByType(String type) {
        QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
        judgeQueryWrapper.eq("judge_type",type)
                .orderByDesc("master")
                .orderByAsc("name");
        List<Judge> judgeList = judgeMapper.selectList(judgeQueryWrapper);
        List<JudgeDrawVO> judgeDrawVOList = new ArrayList<>();
        for(Judge judge : judgeList){
            JudgeDrawVO judgeDrawVO =  new JudgeDrawVO(judge);
            judgeDrawVOList.add(judgeDrawVO);
        }
        return judgeDrawVOList;
    }

    @Override
    public Boolean resetMaster() {
        QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
        List<Judge> judgeList = judgeMapper.selectList(judgeQueryWrapper);
        for (Judge judge : judgeList){
            // 重置
            judge.setMaster(0);
            judgeMapper.updateById(judge);
            // 更改抽签状态
            DrawState drawState4 = drawStateMapper.selectById(4);
            drawState4.setState(false);
            drawStateMapper.updateById(drawState4);
        }
        return true;
    }

    @Override
    public Boolean resetTypeAndMaster() {
        QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
        List<Judge> judgeList = judgeMapper.selectList(judgeQueryWrapper);
        for (Judge judge : judgeList){
            // 重置
            judge.setMaster(0);
            judge.setJudgeType("待抽签");
            judgeMapper.updateById(judge);
            // 更改抽签状态
            DrawState drawState3 = drawStateMapper.selectById(3);
            drawState3.setState(false);
            drawStateMapper.updateById(drawState3);

            DrawState drawState4 = drawStateMapper.selectById(4);
            drawState4.setState(false);
            drawStateMapper.updateById(drawState4);

        }
        return true;
    }
}
