package com.njmetro.evaluation.service.impl;

import com.njmetro.evaluation.domain.TestQuestion;
import com.njmetro.evaluation.mapper.TestQuestionMapper;
import com.njmetro.evaluation.service.TestQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
@Service
@RequiredArgsConstructor
public class TestQuestionServiceImpl extends ServiceImpl<TestQuestionMapper, TestQuestion> implements TestQuestionService {
    private final TestQuestionMapper testQuestionMapper;

    @Override
    public List<TestQuestion> getQuestion() {
        return testQuestionMapper.getQuestion();
    }
}
