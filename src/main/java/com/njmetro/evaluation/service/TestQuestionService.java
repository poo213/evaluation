package com.njmetro.evaluation.service;

import com.njmetro.evaluation.domain.TestQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zc
 * @since 2020-09-21
 */
public interface TestQuestionService extends IService<TestQuestion> {
    List<TestQuestion> getQuestion();
}
