package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.DrawState;
import com.njmetro.evaluation.mapper.DrawStateMapper;
import com.njmetro.evaluation.service.DrawStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-10-02
 */
@Service
@Slf4j
public class DrawStateServiceImpl extends ServiceImpl<DrawStateMapper, DrawState> implements DrawStateService {
    @Autowired
    DrawStateMapper drawStateMapper;

    @Override
    public Integer setDrawStatusFalse(Integer id) {
        DrawState drawState = drawStateMapper.selectById(id);
        drawState.setState(false);
        return drawStateMapper.updateById(drawState);
    }
}
