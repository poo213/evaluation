package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.PauseRecord;
import com.njmetro.evaluation.mapper.PauseRecordMapper;
import com.njmetro.evaluation.service.PauseRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.PauseRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
@Service
@RequiredArgsConstructor
public class PauseRecordServiceImpl extends ServiceImpl<PauseRecordMapper, PauseRecord> implements PauseRecordService {
private final PauseRecordMapper pauseRecordMapper;

    @Override
    public List<PauseRecordVO> getPauseAdjustList(String name) {
        return pauseRecordMapper.getPauseAdjustList(name);
    }
}
