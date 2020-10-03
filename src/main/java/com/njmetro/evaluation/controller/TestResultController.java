package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.service.TestResultService;
import com.njmetro.evaluation.vo.TestResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zc
 * @since 2020-09-30
 */
@RestController
@RequestMapping("/test-result")
@RequiredArgsConstructor
@Slf4j
public class TestResultController {
    private final TestResultService testResultService;

    @GetMapping("/getTempResult")
    public List<TestResultVO> getTempResult(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound) {
        List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
        List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
        for(Integer num :studentIdList)
        {
            List<TestResultVO>  testResultVOArrayList = new ArrayList<>();

            for(TestResultVO testResultVO:testResultVOList){
                if(testResultVO.getStudentId() == num)
                {
                    testResultVOArrayList.add(testResultVO);//将两个裁判的打分存入该list
                }
            }
            if (Math.abs(testResultVOArrayList.get(0).getResult() - testResultVOArrayList.get(1).getResult())>=10)
            {
                log.info("考生{}得分差的绝对值>=10",testResultVOArrayList.get(0).getStudentName());
                for(TestResultVO testResultVO:testResultVOList)
                {
                    if(testResultVO.getStudentId() == testResultVOArrayList.get(0).getStudentId())
                    {
                        testResultVO.setFlag(1);//标记分差大的项
                    }
                }
            }
        }
        int i=1;
        for(TestResultVO testResultVO:testResultVOList)
        {
            testResultVO.setId(i);
            i++;
        }
        return testResultVOList;
    }
}

