package com.njmetro.evaluation.controller;


import com.njmetro.evaluation.service.StudentService;
import com.njmetro.evaluation.service.TestResultService;
import com.njmetro.evaluation.vo.FinalResultVO;
import com.njmetro.evaluation.vo.TestResultVO;
import com.zaxxer.hikari.util.FastList;
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
    private final StudentService studentService;

    @GetMapping("/getTempResult")
    public List<TestResultVO> getTempResult(@RequestParam("gameNumber") Integer gameNumber, @RequestParam("gameRound") Integer gameRound) {
        List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
        List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
        for (Integer num : studentIdList) {
            List<TestResultVO> testResultVOArrayList = new ArrayList<>();

            for (TestResultVO testResultVO : testResultVOList) {
                if (testResultVO.getStudentId() == num) {
                    testResultVOArrayList.add(testResultVO);//将两个裁判的打分存入该list
                }
            }
            if (Math.abs(testResultVOArrayList.get(0).getResult() - testResultVOArrayList.get(1).getResult()) >= 10) {
                log.info("考生{}得分差的绝对值>=10", testResultVOArrayList.get(0).getStudentName());
                for (TestResultVO testResultVO : testResultVOList) {
                    if (testResultVO.getStudentId() == testResultVOArrayList.get(0).getStudentId()) {
                        testResultVO.setFlag(1);//标记分差大的项
                    }
                }
            }
        }
        int i = 1;
        for (TestResultVO testResultVO : testResultVOList) {
            testResultVO.setId(i);
            i++;
        }
        return testResultVOList;
    }

    /**
     * 最终打分结果汇总
     *
     * @return
     */
    @GetMapping("/getFinalResult")
    public List<FinalResultVO> getFinalResult() {
        List<FinalResultVO> finalTempResultVOList = new ArrayList<>();

        for (int gameNumber = 1; gameNumber <= 7; gameNumber++) {
            for (int gameRound = 1; gameRound <= 3; gameRound++) {
                List<TestResultVO> testResultVOList = testResultService.getTempResult(gameNumber, gameRound);
                List<Integer> studentIdList = testResultService.getStudentIdList(gameNumber, gameRound);
                for (Integer num : studentIdList) {
                    List<TestResultVO> testResultVOArrayList = new ArrayList<>();
                    for (TestResultVO testResultVO : testResultVOList) {
                        if (testResultVO.getStudentId() == num) {
                            testResultVOArrayList.add(testResultVO);//将两个裁判的打分存入该list
                        }
                    }
                    double res = (testResultVOArrayList.get(0).getResult() + testResultVOArrayList.get(1).getResult()) / 2;
                    FinalResultVO finalResultVO = new FinalResultVO();
                    finalResultVO.setStudentId(testResultVOArrayList.get(0).getStudentId());
                    finalResultVO.setStudentCode(testResultVOArrayList.get(0).getStudentCode());
                    finalResultVO.setStudentName(testResultVOArrayList.get(0).getStudentName());
                    finalResultVO.setResult(res);
                    finalTempResultVOList.add(finalResultVO);
                }
            }
        }
        List<FinalResultVO> finaResultVOList = new ArrayList<>();//最终展示到前台
        List<Integer> studentIdList = studentService.getStudentIdList();
        log.info("{}", studentIdList);

        for (Integer num : studentIdList) {

            List<FinalResultVO> tempList = new ArrayList<>();
            for (var item : finalTempResultVOList) {
                if (item.getStudentId() == num) {
                    tempList.add(item);
                }
            }
            if (tempList.size() > 0) {
                double finalResult = 0;
                for (FinalResultVO item :
                        tempList) {
                    finalResult += item.getResult();
                }
                log.info("{}", tempList);
                log.info("{}", finalResult);
                log.info("{}", tempList.get(0));
                FinalResultVO finalResultVO = new FinalResultVO();
                finalResultVO.setStudentId(tempList.get(0).getStudentId());
                finalResultVO.setStudentCode(tempList.get(0).getStudentCode());
                finalResultVO.setStudentName(tempList.get(0).getStudentName());
                finalResultVO.setResult(finalResult);
                finaResultVOList.add(finalResultVO);
            }
        }
        return finaResultVOList;
    }

}

