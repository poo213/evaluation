package com.njmetro.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njmetro.evaluation.domain.TestQuestionStandard;
import com.njmetro.evaluation.service.TestQuestionStandardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: evaluation
 * @description: 打分
 * @author: zc
 * @create: 2020-09-23 14:59
 **/
@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
@Slf4j
public class GradeController {
    private final TestQuestionStandardService testQuestionStandardService;

    @GetMapping("/getStandard")
    public List<TestQuestionStandard> getStandard()
    {
        QueryWrapper<TestQuestionStandard> questionStandardQueryWrapper = new QueryWrapper<>();
        //后续此处题号来源于主裁判参数表
        questionStandardQueryWrapper.eq("test_question_id",1);
        return testQuestionStandardService.list(questionStandardQueryWrapper);
    }
}
