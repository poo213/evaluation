package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.domain.TestFinalResult;
import com.njmetro.evaluation.service.TestFinalResultService;
import com.njmetro.evaluation.vo.TestCompanyResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-10-26
 */
@RestController
@RequestMapping("/test-final-result")
@Slf4j
@RequiredArgsConstructor
public class TestFinalResultController {
    private final TestFinalResultService testFinalResultService;

    /**
     * 保存结果
     *
     * @param finalResult
     * @return
     */
    @PostMapping("/saveResult")
    public boolean saveResult(@RequestBody List<TestFinalResult> finalResult) {
        return testFinalResultService.saveBatch(finalResult);
    }

    @GetMapping("/getCompanyResult")
    public List<TestCompanyResultVO> getCompanyResult() {
        List<TestCompanyResultVO> testCompanyResultVOList=testFinalResultService.getCompanyResult();
        Integer i = 1;
        for (TestCompanyResultVO item :testCompanyResultVOList
             ) {
            item.setId(i);
            i++;
        }
        return testCompanyResultVOList;
    }
}

