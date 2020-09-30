package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.Judge;
import com.njmetro.evaluation.dto.JudgeInfoDTO;
import com.njmetro.evaluation.mapper.JudgeMapper;
import com.njmetro.evaluation.service.JudgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<JudgeInfoDTO> getJudgeInfo(String ip) {
        return judgeMapper.getInfo(ip);
    }
}
