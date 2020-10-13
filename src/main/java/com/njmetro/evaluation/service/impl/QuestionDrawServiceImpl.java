package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.QuestionDraw;
import com.njmetro.evaluation.mapper.QuestionDrawMapper;
import com.njmetro.evaluation.service.QuestionDrawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njmetro.evaluation.vo.QuestionDrawVO;
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
public class QuestionDrawServiceImpl extends ServiceImpl<QuestionDrawMapper, QuestionDraw> implements QuestionDrawService {

    @Autowired
    QuestionDrawMapper questionDrawMapper;

    @Override
    public List<QuestionDrawVO> selectList() {
        return questionDrawMapper.selectList();
    }
}
